package com.david.f1stats.ui.favorites

import com.david.f1stats.domain.model.FavoriteRace
import com.david.f1stats.domain.useCases.DeleteFavoriteUseCase
import com.david.f1stats.domain.useCases.GetAllFavoriteRacesUseCase
import com.david.f1stats.ui.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getAllFavoriteRacesUseCase: GetAllFavoriteRacesUseCase = mockk()
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase = mockk()

    @Test
    fun `favoriteRaces emits favorites from use case`() = runTest {
        val favorites = listOf(
            FavoriteRace(1, "Spanish GP", "Spain", "2024"),
            FavoriteRace(2, "Monaco GP", "Monaco", "2024")
        )
        every { getAllFavoriteRacesUseCase() } returns flowOf(favorites)

        val viewModel = FavoritesViewModel(getAllFavoriteRacesUseCase, deleteFavoriteUseCase)
        // stateIn(WhileSubscribed) requires an active collector to start sharing
        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.favoriteRaces.collect {}
        }
        advanceUntilIdle()

        assertEquals(2, viewModel.favoriteRaces.value.size)
        job.cancel()
    }

    @Test
    fun `deleteFavorite calls use case and sets isDeleted`() = runTest {
        every { getAllFavoriteRacesUseCase() } returns flowOf(emptyList())
        coEvery { deleteFavoriteUseCase(100) } returns Unit

        val viewModel = FavoritesViewModel(getAllFavoriteRacesUseCase, deleteFavoriteUseCase)
        advanceUntilIdle()

        viewModel.deleteFavorite(100)
        advanceUntilIdle()

        assertTrue(viewModel.isDeleted.value)
        coVerify { deleteFavoriteUseCase(100) }
    }

    @Test
    fun `clearIsDeleted resets flag`() = runTest {
        every { getAllFavoriteRacesUseCase() } returns flowOf(emptyList())
        coEvery { deleteFavoriteUseCase(any()) } returns Unit

        val viewModel = FavoritesViewModel(getAllFavoriteRacesUseCase, deleteFavoriteUseCase)
        advanceUntilIdle()

        viewModel.deleteFavorite(1)
        advanceUntilIdle()
        viewModel.clearIsDeleted()

        assertFalse(viewModel.isDeleted.value)
    }

    @Test
    fun `delete error sets error message`() = runTest {
        every { getAllFavoriteRacesUseCase() } returns flowOf(emptyList())
        coEvery { deleteFavoriteUseCase(any()) } throws RuntimeException("Delete failed")

        val viewModel = FavoritesViewModel(getAllFavoriteRacesUseCase, deleteFavoriteUseCase)
        advanceUntilIdle()

        viewModel.deleteFavorite(1)
        advanceUntilIdle()

        assertEquals("Delete failed", viewModel.errorMessage.value)
    }

    @Test
    fun `clearErrorMessage resets error`() = runTest {
        every { getAllFavoriteRacesUseCase() } returns flowOf(emptyList())
        coEvery { deleteFavoriteUseCase(any()) } throws RuntimeException("Error")

        val viewModel = FavoritesViewModel(getAllFavoriteRacesUseCase, deleteFavoriteUseCase)
        advanceUntilIdle()
        viewModel.deleteFavorite(1)
        advanceUntilIdle()
        viewModel.clearErrorMessage()

        assertNull(viewModel.errorMessage.value)
    }
}
