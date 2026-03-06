package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.circuit.CircuitCompetitionData
import com.david.f1stats.data.model.circuit.CircuitData
import com.david.f1stats.data.model.circuit.CircuitLapRecordData
import com.david.f1stats.data.model.circuit.CircuitLocationData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CircuitMapperTest {

    private val mapper = CircuitMapper()

    @Test
    fun `fromMap maps circuit data correctly`() {
        val data = listOf(
            CircuitData(
                capacity = 120000,
                competition = CircuitCompetitionData(
                    1,
                    CircuitLocationData("Silverstone", "UK"),
                    "British GP"
                ),
                firstGrandPrix = 1950,
                id = 1,
                image = "http://img.com/silverstone.png",
                lapRecord = CircuitLapRecordData("Hamilton", "1:27.097", "2020"),
                laps = 52,
                length = "5.891km",
                name = "Silverstone Circuit",
                opened = 1948,
                owner = null,
                raceDistance = "306.198km"
            )
        )

        val result = mapper.fromMap(data)!!

        assertEquals(1, result.size)
        assertEquals(1, result[0].id)
        assertEquals("Silverstone Circuit", result[0].name)
        assertEquals("UK", result[0].country)
        assertEquals("5.891km", result[0].length)
        assertEquals("52", result[0].laps)
        assertEquals("1950", result[0].firstGP)
        assertEquals("1:27.097", result[0].lapRecordTime)
        assertEquals("Hamilton", result[0].lapRecordDriver)
    }

    @Test
    fun `fromMap handles null fields with defaults`() {
        val data = listOf(
            CircuitData(null, null, null, null, null, null, null, null, null, null, null, null)
        )

        val result = mapper.fromMap(data)!!

        assertEquals(0, result[0].id)
        assertEquals("Circuit name not found", result[0].name)
        assertEquals("Country not found", result[0].country)
        assertEquals("Length not found", result[0].length)
        assertEquals("Lap record time not found", result[0].lapRecordTime)
        assertEquals("Lap record driver not found", result[0].lapRecordDriver)
        assertEquals("Image not found", result[0].imageURL)
    }

    @Test
    fun `fromMap returns null for null input`() {
        assertNull(mapper.fromMap(null))
    }

    @Test
    fun `fromMap returns empty list for empty input`() {
        val result = mapper.fromMap(emptyList())!!
        assertTrue(result.isEmpty())
    }

    @Test
    fun `fromMap sorts circuits by name`() {
        val data = listOf(
            CircuitData(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "Zandvoort",
                null,
                null,
                null
            ),
            CircuitData(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "Albert Park",
                null,
                null,
                null
            ),
            CircuitData(null, null, null, null, null, null, null, null, "Monaco", null, null, null)
        )

        val result = mapper.fromMap(data)!!

        assertEquals("Albert Park", result[0].name)
        assertEquals("Monaco", result[1].name)
        assertEquals("Zandvoort", result[2].name)
    }
}
