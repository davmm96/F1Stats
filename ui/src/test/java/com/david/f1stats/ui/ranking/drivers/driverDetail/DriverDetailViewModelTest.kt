package com.david.f1stats.ui.ranking.drivers.driverDetail

import com.david.f1stats.domain.model.DriverDetail
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.useCases.GetDriverDetailUseCase
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
class DriverDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getDriverDetailUseCase: GetDriverDetailUseCase = mockk()

    private fun createDriver(name: String = "Verstappen") = DriverDetail(
        name, "Dutch", "NL", "1997-09-30", "img", "#1", "200", "3", "30", "60", "2500", "team"
    )

    @Test
    fun `fetchDriverDetail sets driver info on success`() = runTest {
        val driver = createDriver()
        coEvery { getDriverDetailUseCase(1) } returns Result.Success(driver)

        val viewModel = DriverDetailViewModel(getDriverDetailUseCase)
        viewModel.fetchDriverDetail(1)
        advanceUntilIdle()

        assertEquals(driver, viewModel.driverInfo.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchDriverDetail sets error when name is empty`() = runTest {
        val driver = createDriver(name = "")
        coEvery { getDriverDetailUseCase(1) } returns Result.Success(driver)

        val viewModel = DriverDetailViewModel(getDriverDetailUseCase)
        viewModel.fetchDriverDetail(1)
        advanceUntilIdle()

        assertNull(viewModel.driverInfo.value)
        assertEquals("Driver not found", viewModel.errorMessage.value)
    }

    @Test
    fun `fetchDriverDetail sets error on failure`() = runTest {
        coEvery { getDriverDetailUseCase(1) } returns Result.Error(Exception("Not found"))

        val viewModel = DriverDetailViewModel(getDriverDetailUseCase)
        viewModel.fetchDriverDetail(1)
        advanceUntilIdle()

        assertEquals("Not found", viewModel.errorMessage.value)
    }

    @Test
    fun `clearErrorMessage resets error`() = runTest {
        coEvery { getDriverDetailUseCase(1) } returns Result.Error(Exception("Error"))

        val viewModel = DriverDetailViewModel(getDriverDetailUseCase)
        viewModel.fetchDriverDetail(1)
        advanceUntilIdle()
        viewModel.clearErrorMessage()

        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `isLoading is false initially`() = runTest {
        val viewModel = DriverDetailViewModel(getDriverDetailUseCase)
        assertFalse(viewModel.isLoading.value)
    }
}
