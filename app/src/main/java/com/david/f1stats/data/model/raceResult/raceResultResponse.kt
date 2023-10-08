package com.david.f1stats.data.model.raceResult

data class raceResultResponse(
    val errors: List<Any>,
    val `get`: String,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)