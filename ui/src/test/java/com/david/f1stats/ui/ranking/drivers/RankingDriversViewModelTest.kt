package com.david.f1stats.ui.ranking.drivers

import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.useCases.GetRankingDriverUseCase
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
class RankingDriversViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getRankingDriverUseCase: GetRankingDriverUseCase = mockk()

    @Test
    fun `loads drivers on creation`() = runTest {
        val drivers = listOf(
            RankingDriver(1, 1, "img", "Verstappen", "Red Bull", "575 PTS", 1)
        )
        coEvery { getRankingDriverUseCase() } returns Result.Success(drivers)

        val viewModel = RankingDriversViewModel(getRankingDriverUseCase)
        advanceUntilIdle()

        assertEquals(drivers, viewModel.rankingDriverList.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `error sets error message`() = runTest {
        coEvery { getRankingDriverUseCase() } returns Result.Error(Exception("Error"))

        val viewModel = RankingDriversViewModel(getRankingDriverUseCase)
        advanceUntilIdle()

        assertEquals("Error", viewModel.errorMessage.value)
    }

    @Test
    fun `fetchRankingDrivers can be called manually`() = runTest {
        coEvery { getRankingDriverUseCase() } returns Result.Success(emptyList())

        val viewModel = RankingDriversViewModel(getRankingDriverUseCase)
        advanceUntilIdle()
        assertTrue(viewModel.rankingDriverList.value.isEmpty())

        val drivers = listOf(
            RankingDriver(1, 1, "img", "Verstappen", "Red Bull", "575 PTS", 1)
        )
        coEvery { getRankingDriverUseCase() } returns Result.Success(drivers)
        viewModel.fetchRankingDrivers()
        advanceUntilIdle()

        assertEquals(1, viewModel.rankingDriverList.value.size)
    }

    @Test
    fun `clearErrorMessage resets error`() = runTest {
        coEvery { getRankingDriverUseCase() } returns Result.Error(Exception("Error"))

        val viewModel = RankingDriversViewModel(getRankingDriverUseCase)
        advanceUntilIdle()
        viewModel.clearErrorMessage()

        assertNull(viewModel.errorMessage.value)
    }
}
