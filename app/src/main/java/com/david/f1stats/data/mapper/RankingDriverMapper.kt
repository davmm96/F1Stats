package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.rankingDriver.RankingDriverData
import com.david.f1stats.domain.model.RankingDriver
import javax.inject.Inject

class RankingDriverMapper @Inject constructor(): IMapper<List<RankingDriverData>?, List<RankingDriver>?> {
    override fun fromMap(from: List<RankingDriverData>?): List<RankingDriver>? {
        return from?.map { rankingDriverData ->
            RankingDriver(
                idDriver = rankingDriverData.driver.id,
                position = rankingDriverData.position,
                image = rankingDriverData.driver.image,
                name = rankingDriverData.driver.name,
                team = rankingDriverData.team.name,
                points = rankingDriverData.points.toString() + " PTS"
            )
        }
    }
}