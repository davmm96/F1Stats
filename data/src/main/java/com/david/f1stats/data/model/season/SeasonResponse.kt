package com.david.f1stats.data.model.season

data class SeasonResponse(
    val errors: List<Any>,
    val get: String,
    val parameters: List<Any>,
    val response: List<Int>,
    val results: Int
)
