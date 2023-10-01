package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.rankingTeam.RankingTeamData
import com.david.f1stats.domain.model.RankingTeam
import javax.inject.Inject

class RankingTeamMapper @Inject constructor(): IMapper<List<RankingTeamData>?, List<RankingTeam>?> {
    override fun fromMap(from: List<RankingTeamData>?): List<RankingTeam>? {
        return from?.map { rankingTeamData ->
            RankingTeam(
                idTeam = rankingTeamData.team.id,
                position = rankingTeamData.position.toString(),
                image = rankingTeamData.team.logo,
                name = rankingTeamData.team.name,
                points = rankingTeamData.points.toString()
            )
        }
    }
}