package com.david.f1stats.ui.races.raceDetail

import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.StatusRaceEnum
import com.david.f1stats.domain.model.TypeRaceEnum
import com.david.f1stats.domain.useCases.GetRaceDetailsUseCase
import com.david.f1stats.ui.util.MainDispatcherRule
import com.david.f1stats.utils.CalendarHelper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RaceDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getRaceDetailsUseCase: GetRaceDetailsUseCase = mockk()

    private fun createRaceDetail() = RaceDetail(
        "Silverstone Circuit", "British GP", "22", "Jun", "15:00",
        "UK", 1, "52 laps", TypeRaceEnum.RACE, 1000L, StatusRaceEnum.SCHEDULED
    )

    @Test
    fun `loadData sets race list and race info on success`() = runTest {
        val details = listOf(createRaceDetail())
        coEvery { getRaceDetailsUseCase(1) } returns Result.Success(details)

        val viewModel = RaceDetailViewModel(getRaceDetailsUseCase)
        viewModel.loadData(1)
        advanceUntilIdle()

        assertEquals(details, viewModel.raceList.value)
        assertEquals(details.first(), viewModel.raceInfo.value)
    }

    @Test
    fun `loadData with empty result sets empty list`() = runTest {
        coEvery { getRaceDetailsUseCase(1) } returns Result.Success(emptyList())

        val viewModel = RaceDetailViewModel(getRaceDetailsUseCase)
        viewModel.loadData(1)
        advanceUntilIdle()

        assertTrue(viewModel.raceList.value!!.isEmpty())
        assertNull(viewModel.raceInfo.value)
    }

    @Test
    fun `loadData sets error on failure`() = runTest {
        coEvery { getRaceDetailsUseCase(1) } returns Result.Error(Exception("Error"))

        val viewModel = RaceDetailViewModel(getRaceDetailsUseCase)
        viewModel.loadData(1)
        advanceUntilIdle()

        assertEquals("Error", viewModel.errorMessage.value)
    }

    @Test
    fun `onAddToCalendarRequested sets calendar event`() = runTest {
        val viewModel = RaceDetailViewModel(getRaceDetailsUseCase)
        val event = CalendarHelper.CalendarEvent("Race", "Description", "Circuit", 1000L)

        viewModel.onAddToCalendarRequested(event)

        assertEquals(event, viewModel.addToCalendarEvent.value)
    }

    @Test
    fun `clearErrorMessage resets error`() = runTest {
        coEvery { getRaceDetailsUseCase(1) } returns Result.Error(Exception("Error"))

        val viewModel = RaceDetailViewModel(getRaceDetailsUseCase)
        viewModel.loadData(1)
        advanceUntilIdle()
        viewModel.clearErrorMessage()

        assertNull(viewModel.errorMessage.value)
    }
}
