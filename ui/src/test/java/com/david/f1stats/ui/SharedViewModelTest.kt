package com.david.f1stats.ui

import com.david.f1stats.utils.SeasonManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Test

class SharedViewModelTest {

    @Test
    fun `selectedSeason exposes SeasonManager state`() {
        val flow = MutableStateFlow("2024")
        val seasonManager: SeasonManager = mockk {
            every { currentSeason } returns flow
        }

        val viewModel = SharedViewModel(seasonManager)

        assertEquals("2024", viewModel.selectedSeason.value)
    }

    @Test
    fun `updateSelectedSeason delegates to SeasonManager`() {
        val flow = MutableStateFlow("2023")
        val seasonManager: SeasonManager = mockk(relaxed = true) {
            every { currentSeason } returns flow
        }

        val viewModel = SharedViewModel(seasonManager)
        viewModel.updateSelectedSeason("2024")

        verify { seasonManager.setSelectedSeason("2024") }
    }

    @Test
    fun `selectedSeason reflects SeasonManager updates`() {
        val flow = MutableStateFlow("2023")
        val seasonManager: SeasonManager = mockk {
            every { currentSeason } returns flow
        }

        val viewModel = SharedViewModel(seasonManager)
        assertEquals("2023", viewModel.selectedSeason.value)

        flow.value = "2024"
        assertEquals("2024", viewModel.selectedSeason.value)
    }
}
