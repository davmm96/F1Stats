package com.david.f1stats.data.model.teamDetail

data class TeamDetailResponse(
    val errors: List<Any>,
    val get: String,
    val parameters: TeamDetailParametersData,
    val response: List<TeamDetailData>,
    val results: Int
)