package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.RaceRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetRacesUseCaseTest {

    private val repository: RaceRepository = mockk()
    private val useCase = GetRacesUseCase(repository)

    @Test
    fun `invoke returns success with races`() = runTest {
        val races = listOf(
            Race("Spanish GP", "20-22", "Jun", "Spain", 1, 100, "66 laps", "2024")
        )
        coEvery { repository.getRaces() } returns Result.Success(races)

        val result = useCase()

        assertTrue(result is Result.Success)
        assertEquals(races, (result as Result.Success).data)
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        val exception = Exception("Timeout")
        coEvery { repository.getRaces() } returns Result.Error(exception)

        val result = useCase()

        assertTrue(result is Result.Error)
        assertEquals("Timeout", (result as Result.Error).exception.message)
    }
}
