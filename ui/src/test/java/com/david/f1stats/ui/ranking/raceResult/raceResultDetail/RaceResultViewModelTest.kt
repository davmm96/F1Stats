package com.david.f1stats.ui.ranking.raceResult.raceResultDetail

import com.david.f1stats.domain.model.RaceResult
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.useCases.GetRaceResultUseCase
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
class RaceResultViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getRaceResultUseCase: GetRaceResultUseCase = mockk()

    @Test
    fun `fetchRaceResult sets results on success`() = runTest {
        val results = listOf(
            RaceResult("1", "VER", "1:30:00", "25", 1),
            RaceResult("2", "HAM", "+5.123s", "18", 2)
        )
        coEvery { getRaceResultUseCase(1) } returns Result.Success(results)

        val viewModel = RaceResultViewModel(getRaceResultUseCase)
        viewModel.fetchRaceResult(1)
        advanceUntilIdle()

        assertEquals(results, viewModel.raceResult.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchRaceResult sets error on failure`() = runTest {
        coEvery { getRaceResultUseCase(1) } returns Result.Error(Exception("Error"))

        val viewModel = RaceResultViewModel(getRaceResultUseCase)
        viewModel.fetchRaceResult(1)
        advanceUntilIdle()

        assertEquals("Error", viewModel.errorMessage.value)
        assertTrue(viewModel.raceResult.value.isEmpty())
    }

    @Test
    fun `clearErrorMessage resets error`() = runTest {
        coEvery { getRaceResultUseCase(1) } returns Result.Error(Exception("Error"))

        val viewModel = RaceResultViewModel(getRaceResultUseCase)
        viewModel.fetchRaceResult(1)
        advanceUntilIdle()
        viewModel.clearErrorMessage()

        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `isLoading is false initially`() {
        val viewModel = RaceResultViewModel(getRaceResultUseCase)
        assertFalse(viewModel.isLoading.value)
    }
}
