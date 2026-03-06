package com.david.f1stats.ui.settings

import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.Season
import com.david.f1stats.domain.useCases.GetSeasonsUseCase
import com.david.f1stats.ui.util.MainDispatcherRule
import com.david.f1stats.utils.MusicHelper
import com.david.f1stats.utils.PreferencesHelper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val musicHelper: MusicHelper = mockk(relaxed = true)
    private val preferencesHelper: PreferencesHelper = mockk(relaxed = true) {
        every { musicActivated } returns false
        every { themeMode } returns -1
    }
    private val getSeasonsUseCase: GetSeasonsUseCase = mockk()

    private fun createViewModel(): SettingsViewModel {
        return SettingsViewModel(musicHelper, preferencesHelper, getSeasonsUseCase)
    }

    @Test
    fun `loads seasons on creation`() = runTest {
        val seasons = listOf(Season("2024"), Season("2023"))
        coEvery { getSeasonsUseCase() } returns Result.Success(seasons)

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(seasons, viewModel.seasonList.value)
    }

    @Test
    fun `toggleMusic plays music when enabled`() = runTest {
        coEvery { getSeasonsUseCase() } returns Result.Success(emptyList())

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.toggleMusic(true)

        assertTrue(viewModel.isMusicPlaying.value)
        verify { preferencesHelper.musicActivated = true }
        verify { musicHelper.playMusic() }
    }

    @Test
    fun `toggleMusic pauses music when disabled`() = runTest {
        coEvery { getSeasonsUseCase() } returns Result.Success(emptyList())

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.toggleMusic(false)

        assertFalse(viewModel.isMusicPlaying.value)
        verify { preferencesHelper.musicActivated = false }
        verify { musicHelper.pauseMusic() }
    }

    @Test
    fun `setThemeMode updates preferences and state`() = runTest {
        coEvery { getSeasonsUseCase() } returns Result.Success(emptyList())

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.setThemeMode(2)

        assertEquals(2, viewModel.themeMode.value)
        verify { preferencesHelper.themeMode = 2 }
    }

    @Test
    fun `error fetching seasons sets error message`() = runTest {
        coEvery { getSeasonsUseCase() } returns Result.Error(Exception("Network error"))

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals("Network error", viewModel.errorMessage.value)
    }

    @Test
    fun `clearErrorMessage resets error`() = runTest {
        coEvery { getSeasonsUseCase() } returns Result.Error(Exception("Error"))

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.clearErrorMessage()

        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `initializes music state from preferences`() = runTest {
        every { preferencesHelper.musicActivated } returns true
        coEvery { getSeasonsUseCase() } returns Result.Success(emptyList())

        val viewModel = createViewModel()

        assertTrue(viewModel.isMusicPlaying.value)
    }
}
