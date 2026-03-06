package com.david.f1stats.ui.ranking.teams.teamDetail

import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.TeamDetail
import com.david.f1stats.domain.useCases.GetTeamDetailUseCase
import com.david.f1stats.ui.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TeamDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getTeamDetailUseCase: GetTeamDetailUseCase = mockk()

    private fun createTeam(name: String = "Red Bull") = TeamDetail(
        name, "Milton Keynes", "img", "2005", "6", "120", "100", "90"
    )

    @Test
    fun `fetchTeamDetail sets team info on success`() = runTest {
        val team = createTeam()
        coEvery { getTeamDetailUseCase(1) } returns Result.Success(team)

        val viewModel = TeamDetailViewModel(getTeamDetailUseCase)
        viewModel.fetchTeamDetail(1)
        advanceUntilIdle()

        assertEquals(team, viewModel.teamInfo.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchTeamDetail sets error when name is empty`() = runTest {
        val team = createTeam(name = "")
        coEvery { getTeamDetailUseCase(1) } returns Result.Success(team)

        val viewModel = TeamDetailViewModel(getTeamDetailUseCase)
        viewModel.fetchTeamDetail(1)
        advanceUntilIdle()

        assertNull(viewModel.teamInfo.value)
        assertEquals("Team not found", viewModel.errorMessage.value)
    }

    @Test
    fun `fetchTeamDetail sets error on failure`() = runTest {
        coEvery { getTeamDetailUseCase(1) } returns Result.Error(Exception("Network error"))

        val viewModel = TeamDetailViewModel(getTeamDetailUseCase)
        viewModel.fetchTeamDetail(1)
        advanceUntilIdle()

        assertEquals("Network error", viewModel.errorMessage.value)
    }

    @Test
    fun `clearErrorMessage resets error`() = runTest {
        coEvery { getTeamDetailUseCase(1) } returns Result.Error(Exception("Error"))

        val viewModel = TeamDetailViewModel(getTeamDetailUseCase)
        viewModel.fetchTeamDetail(1)
        advanceUntilIdle()
        viewModel.clearErrorMessage()

        assertNull(viewModel.errorMessage.value)
    }
}
