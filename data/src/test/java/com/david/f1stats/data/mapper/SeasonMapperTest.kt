package com.david.f1stats.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SeasonMapperTest {

    private val mapper = SeasonMapper()

    @Test
    fun `fromMap maps seasons and reverses order`() {
        val data = listOf(2020, 2021, 2022, 2023, 2024)

        val result = mapper.fromMap(data)!!

        assertEquals(5, result.size)
        assertEquals("2024", result[0].season)
        assertEquals("2023", result[1].season)
        assertEquals("2020", result[4].season)
    }

    @Test
    fun `fromMap returns null for null input`() {
        assertNull(mapper.fromMap(null))
    }

    @Test
    fun `fromMap returns empty list for empty input`() {
        assertTrue(mapper.fromMap(emptyList())!!.isEmpty())
    }

    @Test
    fun `fromMap converts integers to strings`() {
        val result = mapper.fromMap(listOf(2024))!!
        assertEquals("2024", result[0].season)
    }
}
