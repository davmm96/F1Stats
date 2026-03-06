package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.rankingTeam.RankingTeamData
import com.david.f1stats.data.model.rankingTeam.RankingTeamInfoData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RankingTeamMapperTest {

    private val mapper = RankingTeamMapper()

    @Test
    fun `fromMap maps team ranking correctly`() {
        val data = listOf(
            RankingTeamData(
                points = 860.0f,
                position = 1,
                season = 2024,
                team = RankingTeamInfoData(1, "logo_url", "Red Bull")
            )
        )

        val result = mapper.fromMap(data)!!

        assertEquals(1, result[0].idTeam)
        assertEquals(1, result[0].position)
        assertEquals("logo_url", result[0].image)
        assertEquals("Red Bull", result[0].name)
        assertEquals("860 PTS", result[0].points)
    }

    @Test
    fun `formatPoints handles fractional points`() {
        val data = listOf(
            RankingTeamData(99.5f, 1, 2024, RankingTeamInfoData(1, "logo", "Team"))
        )
        val result = mapper.fromMap(data)!!

        assertEquals("99.5 PTS", result[0].points)
    }

    @Test
    fun `fromMap returns null for null input`() {
        assertNull(mapper.fromMap(null))
    }
}
