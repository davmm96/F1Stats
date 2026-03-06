package com.david.f1stats.ui.ranking.teams

import com.david.f1stats.domain.model.RankingTeam
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.useCases.GetRankingTeamUseCase
import com.david.f1stats.ui.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RankingTeamsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getRankingTeamUseCase: GetRankingTeamUseCase = mockk()

    @Test
    fun `loads teams on creation`() = runTest {
        val teams = listOf(RankingTeam(1, "860 PTS", 1, "Red Bull", "logo"))
        coEvery { getRankingTeamUseCase() } returns Result.Success(teams)

        val viewModel = RankingTeamsViewModel(getRankingTeamUseCase)
        advanceUntilIdle()

        assertEquals(teams, viewModel.rankingTeamList.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `error sets error message`() = runTest {
        coEvery { getRankingTeamUseCase() } returns Result.Error(Exception("Network error"))

        val viewModel = RankingTeamsViewModel(getRankingTeamUseCase)
        advanceUntilIdle()

        assertEquals("Network error", viewModel.errorMessage.value)
    }

    @Test
    fun `empty result sets empty list`() = runTest {
        coEvery { getRankingTeamUseCase() } returns Result.Success(emptyList())

        val viewModel = RankingTeamsViewModel(getRankingTeamUseCase)
        advanceUntilIdle()

        assertTrue(viewModel.rankingTeamList.value.isEmpty())
    }
}
