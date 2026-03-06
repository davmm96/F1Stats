package com.david.f1stats.ui.ranking.raceResult

import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.useCases.DeleteFavoriteUseCase
import com.david.f1stats.domain.useCases.GetAllFavoriteRacesIdsUseCase
import com.david.f1stats.domain.useCases.GetRaceCompletedUseCase
import com.david.f1stats.domain.useCases.InsertFavoriteRaceUseCase
import com.david.f1stats.ui.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
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
class RankingRacesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getRaceCompletedUseCase: GetRaceCompletedUseCase = mockk()
    private val getAllFavoriteRacesIdsUseCase: GetAllFavoriteRacesIdsUseCase = mockk()
    private val insertFavoriteRaceUseCase: InsertFavoriteRaceUseCase = mockk()
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase = mockk()

    private fun createViewModel(): RankingRacesViewModel {
        coEvery { getAllFavoriteRacesIdsUseCase() } returns emptyList()
        return RankingRacesViewModel(
            getRaceCompletedUseCase,
            getAllFavoriteRacesIdsUseCase,
            insertFavoriteRaceUseCase,
            deleteFavoriteUseCase
        )
    }

    @Test
    fun `loads completed races on creation`() = runTest {
        val races = listOf(Race("Spanish GP", "20-22", "Jun", "Spain", 1, 100, "66 laps", "2024"))
        coEvery { getRaceCompletedUseCase() } returns Result.Success(races)

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(races, viewModel.racesCompletedList.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `addRaceToFavorites inserts and refreshes ids`() = runTest {
        val race = Race("GP", "1-3", "Jan", "C", 1, 100, "50 laps", "2024")
        coEvery { getRaceCompletedUseCase() } returns Result.Success(emptyList())
        coEvery { insertFavoriteRaceUseCase(race) } returns Unit

        val viewModel = createViewModel()
        advanceUntilIdle()

        // After init, re-stub to return the newly-added id
        coEvery { getAllFavoriteRacesIdsUseCase() } returns listOf(100)
        viewModel.addRaceToFavorites(race)
        advanceUntilIdle()

        assertEquals(true, viewModel.favoriteMessage.value)
        assertTrue(viewModel.favoriteRacesIds.value.contains(100))
        coVerify { insertFavoriteRaceUseCase(race) }
    }

    @Test
    fun `removeRaceFromFavorites deletes and refreshes ids`() = runTest {
        coEvery { getRaceCompletedUseCase() } returns Result.Success(emptyList())
        coEvery { deleteFavoriteUseCase(100) } returns Unit
        coEvery { getAllFavoriteRacesIdsUseCase() } returns emptyList()

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.removeRaceFromFavorites(100)
        advanceUntilIdle()

        assertEquals(false, viewModel.favoriteMessage.value)
        coVerify { deleteFavoriteUseCase(100) }
    }

    @Test
    fun `clearFavoriteMessage resets message`() = runTest {
        coEvery { getRaceCompletedUseCase() } returns Result.Success(emptyList())
        coEvery { insertFavoriteRaceUseCase(any()) } returns Unit

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.addRaceToFavorites(Race("GP", "1-3", "Jan", "C", 1, 1, "50", "2024"))
        advanceUntilIdle()
        viewModel.clearFavoriteMessage()

        assertNull(viewModel.favoriteMessage.value)
    }

    @Test
    fun `error sets error message`() = runTest {
        coEvery { getRaceCompletedUseCase() } returns Result.Error(Exception("Error"))

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals("Error", viewModel.errorMessage.value)
    }

    @Test
    fun `clearErrorMessage resets error`() = runTest {
        coEvery { getRaceCompletedUseCase() } returns Result.Error(Exception("Error"))

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.clearErrorMessage()

        assertNull(viewModel.errorMessage.value)
    }
}
