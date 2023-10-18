package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.rankingDriver.RankingDriverData
import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.utils.formatPoints
import javax.inject.Inject

class RankingDriverMapper @Inject constructor(): IMapper<List<RankingDriverData>?, List<RankingDriver>?> {

    override fun fromMap(from: List<RankingDriverData>?): List<RankingDriver>? {
        return from?.map { it.toRankingDriver() }
    }

    private fun RankingDriverData.toRankingDriver() = RankingDriver(
        idDriver = driver.id,
        position = position,
        image = driver.image,
        name = driver.name,
        team = team.name,
        points = "${points.formatPoints()} PTS",
        idTeam = team.id
    )
}
