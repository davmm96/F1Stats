package com.david.f1stats.data.model.rankingDriver

data class RankingDriverResponse(
    val errors: List<Any>,
    val get: String,
    val parameters: RankingDriverParameters,
    val response: List<RankingDriverData>,
    val results: Int
)
