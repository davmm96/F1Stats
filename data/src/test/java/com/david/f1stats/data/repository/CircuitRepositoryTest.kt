package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.CircuitMapper
import com.david.f1stats.data.model.circuit.CircuitData
import com.david.f1stats.data.source.network.CircuitService
import com.david.f1stats.domain.model.Circuit
import com.david.f1stats.domain.model.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CircuitRepositoryTest {

    private val circuitService: CircuitService = mockk()
    private val circuitMapper: CircuitMapper = mockk()
    private val repository = CircuitRepository(circuitService, circuitMapper)

    @Test
    fun `getCircuits returns mapped data on success`() = runTest {
        val rawData = listOf<CircuitData>(mockk())
        val mapped =
            listOf(Circuit(1, "Silverstone", "UK", "5.891km", "52", "1950", "1:27", "HAM", "url"))
        coEvery { circuitService.getCircuits() } returns Result.Success(rawData)
        every { circuitMapper.fromMap(rawData) } returns mapped

        val result = repository.getCircuits()

        assertTrue(result is Result.Success)
        assertEquals(mapped, (result as Result.Success).data)
    }

    @Test
    fun `getCircuits returns empty list when mapper returns null`() = runTest {
        coEvery { circuitService.getCircuits() } returns Result.Success(emptyList())
        every { circuitMapper.fromMap(any()) } returns null

        val result = repository.getCircuits()

        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data.isEmpty())
    }

    @Test
    fun `getCircuits returns error when service fails`() = runTest {
        val exception = Exception("Network error")
        coEvery { circuitService.getCircuits() } returns Result.Error(exception)

        val result = repository.getCircuits()

        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }
}
