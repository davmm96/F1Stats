package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.rankingTeam.RankingTeamData
import com.david.f1stats.domain.model.RankingTeam
import javax.inject.Inject

class RankingTeamMapper @Inject constructor(): IMapper<List<RankingTeamData>?, List<RankingTeam>?> {
    override fun fromMap(from: List<RankingTeamData>?): List<RankingTeam>? {
        return from?.map { rankingTeamData ->
            RankingTeam(
                idTeam = rankingTeamData.team.id,
                position = rankingTeamData.position,
                image = rankingTeamData.team.logo,
                name = rankingTeamData.team.name,
                points = formatPoints(rankingTeamData.points) + " PTS"
            )
        }
    }

    private fun formatPoints(points: Float): String {
        return if ((points % 1).toDouble() == 0.0) {
            points.toInt().toString()
        } else {
            points.toString()
        }
    }
}