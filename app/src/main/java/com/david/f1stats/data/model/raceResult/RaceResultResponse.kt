package com.david.f1stats.data.model.raceResult

data class RaceResultResponse(
    val errors: List<Any>,
    val get: String,
    val parameters: RaceResultParametersData,
    val response: List<RaceResultData>,
    val results: Int
)