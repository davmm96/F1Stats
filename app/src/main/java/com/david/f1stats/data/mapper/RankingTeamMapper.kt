package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.rankingTeam.RankingTeamData
import com.david.f1stats.domain.model.RankingTeam
import com.david.f1stats.utils.formatPoints
import javax.inject.Inject

class RankingTeamMapper @Inject constructor(): IMapper<List<RankingTeamData>?, List<RankingTeam>?> {

    override fun fromMap(from: List<RankingTeamData>?): List<RankingTeam>? {
        return from?.map { it.toRankingTeam() }
    }

    private fun RankingTeamData.toRankingTeam() = RankingTeam(
        idTeam = team.id,
        position = position,
        image = team.logo,
        name = team.name,
        points = "${points.formatPoints()} PTS"
    )
}