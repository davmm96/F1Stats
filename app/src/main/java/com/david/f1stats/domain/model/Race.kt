package com.david.f1stats.domain.model

data class Race(
    val competition: String,
    val dayInterval: String,
    val month: String,
    val country: String,
    val id: Int,
    val laps: String,
)
