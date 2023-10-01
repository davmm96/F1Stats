package com.david.f1stats.data.model.circuit

data class CircuitResponse(
    val errors: List<Any>,
    val get: String,
    val parameters: List<Any>,
    val response: List<CircuitData>,
    val results: Int
)