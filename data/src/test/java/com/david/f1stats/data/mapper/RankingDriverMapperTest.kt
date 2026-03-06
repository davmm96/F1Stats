package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.rankingDriver.RankingDriverData
import com.david.f1stats.data.model.rankingDriver.RankingDriverInfoData
import com.david.f1stats.data.model.rankingDriver.RankingDriverTeamData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RankingDriverMapperTest {

    private val mapper = RankingDriverMapper()

    private fun createDriverData(
        position: Int = 1,
        points: Float = 575.0f,
        driverId: Int = 1,
        driverName: String = "Verstappen",
        teamId: Int = 1,
        teamName: String = "Red Bull"
    ) = RankingDriverData(
        behind = 0,
        driver = RankingDriverInfoData("VER", driverId, "img", driverName, 1),
        points = points,
        position = position,
        season = 2024,
        team = RankingDriverTeamData(teamId, "logo", teamName),
        wins = 19
    )

    @Test
    fun `fromMap maps driver ranking correctly`() {
        val data = listOf(createDriverData())

        val result = mapper.fromMap(data)!!

        assertEquals(1, result[0].idDriver)
        assertEquals(1, result[0].position)
        assertEquals("img", result[0].image)
        assertEquals("Verstappen", result[0].name)
        assertEquals("Red Bull", result[0].team)
        assertEquals("575 PTS", result[0].points)
        assertEquals(1, result[0].idTeam)
    }

    @Test
    fun `formatPoints removes decimal for whole numbers`() {
        val data = listOf(createDriverData(points = 100.0f))
        val result = mapper.fromMap(data)!!

        assertEquals("100 PTS", result[0].points)
    }

    @Test
    fun `formatPoints keeps decimal for fractional points`() {
        val data = listOf(createDriverData(points = 100.5f))
        val result = mapper.fromMap(data)!!

        assertEquals("100.5 PTS", result[0].points)
    }

    @Test
    fun `fromMap returns null for null input`() {
        assertNull(mapper.fromMap(null))
    }

    @Test
    fun `fromMap maps multiple drivers preserving order`() {
        val data = listOf(
            createDriverData(position = 1, driverName = "Verstappen"),
            createDriverData(position = 2, driverName = "Hamilton", driverId = 2)
        )
        val result = mapper.fromMap(data)!!

        assertEquals(2, result.size)
        assertEquals("Verstappen", result[0].name)
        assertEquals("Hamilton", result[1].name)
    }
}
