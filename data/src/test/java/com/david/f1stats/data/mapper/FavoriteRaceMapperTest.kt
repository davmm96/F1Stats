package com.david.f1stats.data.mapper

import com.david.f1stats.domain.model.Race
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteRaceMapperTest {

    private val mapper = FavoriteRaceMapper()

    @Test
    fun `fromMap maps Race to FavoriteRace entity`() {
        val race = Race(
            competition = "Spanish GP",
            dayInterval = "20-22",
            month = "Jun",
            country = "Spain",
            idCompetition = 1,
            idRace = 100,
            laps = "66 laps",
            season = "2024"
        )

        val result = mapper.fromMap(race)

        assertEquals(100, result.id)
        assertEquals("Spanish GP", result.competition)
        assertEquals("Spain", result.country)
        assertEquals("2024", result.season)
    }

    @Test
    fun `fromMap uses idRace as primary key`() {
        val race = Race("GP", "1-3", "Jan", "Country", 5, 42, "50 laps", "2023")

        val result = mapper.fromMap(race)

        assertEquals(42, result.id)
    }
}
