package com.david.f1stats.data.model.race

data class RaceResponse(
    val errors: List<Any>,
    val get: String,
    val parameters: Parameters,
    val response: List<RaceData>,
    val results: Int
)
