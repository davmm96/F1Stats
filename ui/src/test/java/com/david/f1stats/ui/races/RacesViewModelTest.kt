package com.david.f1stats.ui.races

import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.useCases.GetRacesUseCase
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
class RacesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getRacesUseCase: GetRacesUseCase = mockk()

    private fun createViewModel(): RacesViewModel {
        return RacesViewModel(getRacesUseCase)
    }

    @Test
    fun `initial state loads races on creation`() = runTest {
        val races = listOf(
            Race("Spanish GP", "20-22", "Jun", "Spain", 1, 100, "66 laps", "2024")
        )
        coEvery { getRacesUseCase() } returns Result.Success(races)

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(races, viewModel.raceList.value)
        assertFalse(viewModel.isSeasonCompleted.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `empty result marks season as completed`() = runTest {
        coEvery { getRacesUseCase() } returns Result.Success(emptyList())

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertTrue(viewModel.isSeasonCompleted.value)
        assertTrue(viewModel.raceList.value!!.isEmpty())
    }

    @Test
    fun `error sets error message`() = runTest {
        coEvery { getRacesUseCase() } returns Result.Error(Exception("Network error"))

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals("Network error", viewModel.errorMessage.value)
    }

    @Test
    fun `clearErrorMessage resets error`() = runTest {
        coEvery { getRacesUseCase() } returns Result.Error(Exception("Error"))

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.clearErrorMessage()

        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `fetchRaces can be called again after error`() = runTest {
        coEvery { getRacesUseCase() } returns Result.Error(Exception("Error"))

        val viewModel = createViewModel()
        advanceUntilIdle()

        val races = listOf(Race("GP", "1-3", "Jan", "C", 1, 1, "50 laps", "2024"))
        coEvery { getRacesUseCase() } returns Result.Success(races)
        viewModel.fetchRaces()
        advanceUntilIdle()

        assertEquals(races, viewModel.raceList.value)
    }

    @Test
    fun `loading is false after fetch completes`() = runTest {
        coEvery { getRacesUseCase() } returns Result.Success(emptyList())

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `exception in use case sets error message`() = runTest {
        coEvery { getRacesUseCase() } throws RuntimeException("Crash")

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals("Crash", viewModel.errorMessage.value)
    }
}
