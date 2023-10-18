package com.david.f1stats.data.model.rankingTeam

data class RankingTeamData(
    val points: Float,
    val position: Int,
    val season: Int,
    val team: RankingTeamInfoData
)
