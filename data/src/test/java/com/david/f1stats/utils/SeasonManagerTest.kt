package com.david.f1stats.utils

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SeasonManagerTest {

    @Test
    fun `currentSeason initializes from PreferencesHelper`() {
        val prefs: PreferencesHelper = mockk()
        every { prefs.selectedSeason } returns "2024"

        val manager = SeasonManager(prefs)

        assertEquals("2024", manager.currentSeason.value)
    }

    @Test
    fun `currentSeason defaults to empty when preference is null`() {
        val prefs: PreferencesHelper = mockk()
        every { prefs.selectedSeason } returns null

        val manager = SeasonManager(prefs)

        assertEquals("", manager.currentSeason.value)
    }

    @Test
    fun `setSelectedSeason updates flow and preferences`() = runTest {
        val prefs: PreferencesHelper = mockk(relaxed = true)
        every { prefs.selectedSeason } returns "2023"
        val manager = SeasonManager(prefs)

        manager.setSelectedSeason("2024")

        assertEquals("2024", manager.currentSeason.value)
        verify { prefs.selectedSeason = "2024" }
    }

    @Test
    fun `setSelectedSeason can be called multiple times`() = runTest {
        val prefs: PreferencesHelper = mockk(relaxed = true)
        every { prefs.selectedSeason } returns "2022"
        val manager = SeasonManager(prefs)

        manager.setSelectedSeason("2023")
        manager.setSelectedSeason("2024")

        assertEquals("2024", manager.currentSeason.value)
    }
}
