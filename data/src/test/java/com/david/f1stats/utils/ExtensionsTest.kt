package com.david.f1stats.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `formatDate formats date with day pattern`() {
        val result = formatDate("2024-06-22T15:00:00+00:00", "dd")
        assertEquals("22", result)
    }

    @Test
    fun `formatDate formats date with hour pattern`() {
        val result = formatDate("2024-06-22T15:00:00+00:00", "HH:mm")
        assertEquals("15:00", result)
    }

    @Test
    fun `formatDate formats date with month pattern`() {
        val result = formatDate("2024-06-22T15:00:00+00:00", "MMM")
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `formatIntervalDate returns correct day interval`() {
        val result = formatIntervalDate("2024-06-22T15:00:00+00:00", 2)
        assertEquals("20-22", result)
    }

    @Test
    fun `formatIntervalDate with interval of 0 returns same day`() {
        val result = formatIntervalDate("2024-06-22T15:00:00+00:00", 0)
        assertEquals("22-22", result)
    }

    @Test
    fun `dateToMillis returns positive value`() {
        val result = dateToMillis("2024-06-22T15:00:00+00:00")
        assertTrue(result > 0)
    }

    @Test
    fun `dateToMillis returns consistent values`() {
        val result1 = dateToMillis("2024-06-22T15:00:00+00:00")
        val result2 = dateToMillis("2024-06-22T15:00:00+00:00")
        assertEquals(result1, result2)
    }

    @Test
    fun `dateToMillis earlier date has smaller millis`() {
        val earlier = dateToMillis("2024-06-20T15:00:00+00:00")
        val later = dateToMillis("2024-06-22T15:00:00+00:00")
        assertTrue(earlier < later)
    }

    @Test
    fun `formatPoints removes decimal for whole numbers`() {
        assertEquals("100", 100.0f.formatPoints())
        assertEquals("0", 0.0f.formatPoints())
        assertEquals("575", 575.0f.formatPoints())
    }

    @Test
    fun `formatPoints keeps decimal for fractional numbers`() {
        assertEquals("100.5", 100.5f.formatPoints())
        assertEquals("0.5", 0.5f.formatPoints())
    }
}
