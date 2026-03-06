package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.RankingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetRankingDriverUseCaseTest {

    private val repository: RankingRepository = mockk()
    private val useCase = GetRankingDriverUseCase(repository)

    @Test
    fun `invoke returns success with drivers ranking`() = runTest {
        val drivers = listOf(
            RankingDriver(1, 1, "img", "Verstappen", "Red Bull", "575 PTS", 1)
        )
        coEvery { repository.getRankingDriver() } returns Result.Success(drivers)

        val result = useCase()

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
    }

    @Test
    fun `invoke returns error on failure`() = runTest {
        coEvery { repository.getRankingDriver() } returns Result.Error(Exception("Error"))

        val result = useCase()

        assertTrue(result is Result.Error)
    }
}
