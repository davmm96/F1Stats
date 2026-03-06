package com.david.f1stats.ui.circuits

import com.david.f1stats.domain.model.Circuit
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.useCases.GetCircuitsUseCase
import com.david.f1stats.ui.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CircuitsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getCircuitsUseCase: GetCircuitsUseCase = mockk()

    @Test
    fun `loads circuits on creation`() = runTest {
        val circuits = listOf(
            Circuit(1, "Silverstone", "UK", "5.891km", "52", "1950", "1:27", "HAM", "url")
        )
        coEvery { getCircuitsUseCase() } returns Result.Success(circuits)

        val viewModel = CircuitsViewModel(getCircuitsUseCase)
        advanceUntilIdle()

        assertEquals(circuits, viewModel.circuitsList.value)
        assertFalse(viewModel.isLoading.value)
        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `error sets error message`() = runTest {
        coEvery { getCircuitsUseCase() } returns Result.Error(Exception("Network error"))

        val viewModel = CircuitsViewModel(getCircuitsUseCase)
        advanceUntilIdle()

        assertEquals("Network error", viewModel.errorMessage.value)
    }

    @Test
    fun `empty result sets empty list`() = runTest {
        coEvery { getCircuitsUseCase() } returns Result.Success(emptyList())

        val viewModel = CircuitsViewModel(getCircuitsUseCase)
        advanceUntilIdle()

        assertTrue(viewModel.circuitsList.value.isEmpty())
    }

    @Test
    fun `clearErrorMessage resets error`() = runTest {
        coEvery { getCircuitsUseCase() } returns Result.Error(Exception("Error"))

        val viewModel = CircuitsViewModel(getCircuitsUseCase)
        advanceUntilIdle()
        viewModel.clearErrorMessage()

        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `exception in use case sets error message`() = runTest {
        coEvery { getCircuitsUseCase() } throws RuntimeException("Crash")

        val viewModel = CircuitsViewModel(getCircuitsUseCase)
        advanceUntilIdle()

        assertEquals("Crash", viewModel.errorMessage.value)
    }
}
