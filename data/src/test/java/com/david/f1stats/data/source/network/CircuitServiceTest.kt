package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.circuit.CircuitData
import com.david.f1stats.data.model.circuit.CircuitResponse
import com.david.f1stats.domain.model.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class CircuitServiceTest {

    private val api: APIClient = mockk()
    private val service = CircuitService(api)

    @Test
    fun `getCircuits returns success with data on successful response`() = runTest {
        val circuitData = listOf<CircuitData>(mockk())
        val body = mockk<CircuitResponse> {
            every { response } returns circuitData
        }
        coEvery { api.getCircuits() } returns Response.success(body)

        val result = service.getCircuits()

        assertTrue(result is Result.Success)
        assertEquals(circuitData, (result as Result.Success).data)
    }

    @Test
    fun `getCircuits returns error on exception`() = runTest {
        coEvery { api.getCircuits() } throws RuntimeException("Network failure")

        val result = service.getCircuits()

        assertTrue(result is Result.Error)
        assertEquals("Network failure", (result as Result.Error).exception.message)
    }

    @Test
    fun `getCircuits returns error on unsuccessful response`() = runTest {
        coEvery { api.getCircuits() } returns Response.error(
            500,
            okhttp3.ResponseBody.Companion.create(null, "Server Error")
        )

        val result = service.getCircuits()

        assertTrue(result is Result.Error)
    }
}
