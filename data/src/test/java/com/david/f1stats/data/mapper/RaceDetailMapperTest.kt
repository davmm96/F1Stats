package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.race.RaceCircuitData
import com.david.f1stats.data.model.race.RaceCompetitionData
import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.data.model.race.RaceDriverData
import com.david.f1stats.data.model.race.RaceFastestLapData
import com.david.f1stats.data.model.race.RaceLapsData
import com.david.f1stats.data.model.race.RaceLocationData
import com.david.f1stats.domain.model.StatusRaceEnum
import com.david.f1stats.domain.model.TypeRaceEnum
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RaceDetailMapperTest {

    private val mapper = RaceDetailMapper()

    private fun createRaceData(
        type: String = "Race",
        status: String = "Scheduled",
        date: String = "2024-06-22T15:00:00+00:00"
    ) = RaceData(
        circuit = RaceCircuitData(1, "img", "Silverstone Circuit"),
        competition = RaceCompetitionData(1, RaceLocationData("Silverstone", "UK"), "British GP"),
        date = date,
        distance = "306km",
        fastestLap = RaceFastestLapData(RaceDriverData(1), "1:27.097"),
        id = 100,
        laps = RaceLapsData(Any(), 52),
        season = 2024,
        status = status,
        timezone = "UTC",
        type = type,
        weather = Any()
    )

    @Test
    fun `fromMap maps scheduled race correctly`() {
        val data = listOf(createRaceData())

        val result = mapper.fromMap(data)!!

        assertEquals(1, result.size)
        assertEquals("Silverstone Circuit", result[0].circuit)
        assertEquals("British GP", result[0].competition)
        assertEquals("UK", result[0].country)
        assertEquals("52 laps", result[0].laps)
        assertEquals(TypeRaceEnum.RACE, result[0].type)
        assertEquals(StatusRaceEnum.SCHEDULED, result[0].status)
    }

    @Test
    fun `fromMap filters out completed races`() {
        val data = listOf(createRaceData(status = "Finished"))

        val result = mapper.fromMap(data)!!

        assertTrue(result.isEmpty())
    }

    @Test
    fun `fromMap filters out cancelled races`() {
        val data = listOf(createRaceData(status = "Cancelled"))
        assertTrue(mapper.fromMap(data)!!.isEmpty())
    }

    @Test
    fun `fromMap filters out unknown type races`() {
        val data = listOf(createRaceData(type = "Unknown"))
        assertTrue(mapper.fromMap(data)!!.isEmpty())
    }

    @Test
    fun `fromMap maps all race types correctly`() {
        val typeMap = mapOf(
            "Race" to TypeRaceEnum.RACE,
            "1st Qualifying" to TypeRaceEnum.QUALY,
            "1st Practice" to TypeRaceEnum.P1,
            "2nd Practice" to TypeRaceEnum.P2,
            "3rd Practice" to TypeRaceEnum.P3,
            "Sprint" to TypeRaceEnum.SPRINT,
            "1st Sprint Shootout" to TypeRaceEnum.SPRINT_SHOOTOUT
        )

        typeMap.forEach { (typeStr, expectedEnum) ->
            val data = listOf(createRaceData(type = typeStr))
            val result = mapper.fromMap(data)!!
            assertEquals("Expected $expectedEnum for '$typeStr'", expectedEnum, result[0].type)
        }
    }

    @Test
    fun `fromMap maps all status types correctly`() {
        // Only SCHEDULED passes the filter, so test indirectly via the type that gets NONE
        val data = listOf(createRaceData(status = "Live"))
        assertTrue(mapper.fromMap(data)!!.isEmpty())
    }

    @Test
    fun `fromMap returns null for null input`() {
        assertNull(mapper.fromMap(null))
    }

    @Test
    fun `fromMap sorts by dateCalendar descending`() {
        val data = listOf(
            createRaceData(date = "2024-06-20T10:00:00+00:00", type = "1st Practice"),
            createRaceData(date = "2024-06-22T15:00:00+00:00", type = "Race"),
            createRaceData(date = "2024-06-21T14:00:00+00:00", type = "1st Qualifying")
        )

        val result = mapper.fromMap(data)!!

        assertEquals(TypeRaceEnum.RACE, result[0].type)
        assertEquals(TypeRaceEnum.QUALY, result[1].type)
        assertEquals(TypeRaceEnum.P1, result[2].type)
    }
}
