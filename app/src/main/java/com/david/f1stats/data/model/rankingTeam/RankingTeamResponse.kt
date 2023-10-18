package com.david.f1stats.data.model.rankingTeam

data class RankingTeamResponse(
    val errors: List<Any>,
    val get: String,
    val parameters: RankingTeamParameters,
    val response: List<RankingTeamData>,
    val results: Int
)
