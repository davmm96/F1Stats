package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.Circuit
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.CircuitRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetCircuitsUseCaseTest {

    private val repository: CircuitRepository = mockk()
    private val useCase = GetCircuitsUseCase(repository)

    @Test
    fun `invoke returns success with circuits`() = runTest {
        val circuits = listOf(
            Circuit(1, "Silverstone", "UK", "5.891km", "52", "1950", "1:27.097", "Hamilton", "url")
        )
        coEvery { repository.getCircuits() } returns Result.Success(circuits)

        val result = useCase()

        assertTrue(result is Result.Success)
        assertEquals(circuits, (result as Result.Success).data)
        coVerify(exactly = 1) { repository.getCircuits() }
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        val exception = Exception("Network error")
        coEvery { repository.getCircuits() } returns Result.Error(exception)

        val result = useCase()

        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }

    @Test
    fun `invoke returns success with empty list`() = runTest {
        coEvery { repository.getCircuits() } returns Result.Success(emptyList())

        val result = useCase()

        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data.isEmpty())
    }
}
