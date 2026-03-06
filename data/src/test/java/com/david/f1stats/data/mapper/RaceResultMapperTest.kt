package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.raceResult.RaceResultData
import com.david.f1stats.data.model.raceResult.RaceResultDriverData
import com.david.f1stats.data.model.raceResult.RaceResultRaceData
import com.david.f1stats.data.model.raceResult.RaceResultTeamData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RaceResultMapperTest {

    private val mapper = RaceResultMapper()

    private fun createRaceResultData(
        position: Int,
        abbr: String? = "VER",
        name: String = "Max Verstappen",
        time: String? = "1:30:00",
        gap: String? = null,
        teamId: Int = 1
    ) = RaceResultData(
        driver = RaceResultDriverData(abbr, 1, "img", name, 1),
        gap = gap,
        grid = "1",
        laps = 57,
        pits = 2,
        position = position,
        race = RaceResultRaceData(1),
        team = RaceResultTeamData(teamId, "logo", "Red Bull"),
        time = time
    )

    @Test
    fun `fromMap maps race result correctly`() {
        val data = listOf(createRaceResultData(1))

        val result = mapper.fromMap(data)!!

        assertEquals("1", result[0].position)
        assertEquals("VER", result[0].driverAbbr)
        assertEquals("1:30:00", result[0].time)
        assertEquals("25", result[0].points)
        assertEquals(1, result[0].idTeam)
    }

    @Test
    fun `points are assigned correctly for top 10 positions`() {
        val expectedPoints = mapOf(
            1 to 25,
            2 to 18,
            3 to 15,
            4 to 12,
            5 to 10,
            6 to 8,
            7 to 6,
            8 to 4,
            9 to 2,
            10 to 1
        )

        expectedPoints.forEach { (position, points) ->
            val data = listOf(createRaceResultData(position))
            val result = mapper.fromMap(data)!!
            assertEquals(
                "Position $position should have $points points",
                points.toString(),
                result[0].points
            )
        }
    }

    @Test
    fun `positions beyond 10 get 0 points`() {
        val data = listOf(createRaceResultData(11), createRaceResultData(20))
        val result = mapper.fromMap(data)!!

        assertEquals("0", result[0].points)
        assertEquals("0", result[1].points)
    }

    @Test
    fun `uses gap when time is null`() {
        val data = listOf(createRaceResultData(2, time = null, gap = "+5.123s"))
        val result = mapper.fromMap(data)!!

        assertEquals("+5.123s", result[0].time)
    }

    @Test
    fun `uses dash when both time and gap are null`() {
        val data = listOf(createRaceResultData(2, time = null, gap = null))
        val result = mapper.fromMap(data)!!

        assertEquals("-", result[0].time)
    }

    @Test
    fun `resolves missing abbreviation for known drivers`() {
        val testCases = mapOf(
            "Oscar Piastri" to "PIA",
            "Guanyu Zhou" to "ZHO",
            "Liam Lawson" to "LAW",
            "Logan Sargeant" to "SAR"
        )

        testCases.forEach { (name, expectedAbbr) ->
            val data = listOf(createRaceResultData(1, abbr = "NF", name = name))
            val result = mapper.fromMap(data)!!
            assertEquals("Expected $expectedAbbr for $name", expectedAbbr, result[0].driverAbbr)
        }
    }

    @Test
    fun `keeps NF for unknown driver with missing abbreviation`() {
        val data = listOf(createRaceResultData(1, abbr = "NF", name = "Unknown Driver"))
        val result = mapper.fromMap(data)!!

        assertEquals("NF", result[0].driverAbbr)
    }

    @Test
    fun `uses existing abbreviation when not NF`() {
        val data = listOf(createRaceResultData(1, abbr = "HAM", name = "Lewis Hamilton"))
        val result = mapper.fromMap(data)!!

        assertEquals("HAM", result[0].driverAbbr)
    }

    @Test
    fun `fromMap returns null for null input`() {
        assertNull(mapper.fromMap(null))
    }

    @Test
    fun `fromMap returns empty list for empty input`() {
        assertTrue(mapper.fromMap(emptyList())!!.isEmpty())
    }
}
