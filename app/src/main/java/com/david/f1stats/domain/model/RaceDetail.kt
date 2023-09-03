package com.david.f1stats.domain.model

data class RaceDetail(
    val circuit: String,
    val competition: String,
    val day: String,
    val month: String,
    val hour: String,
    val country: String,
    val id: Int,
    val laps: String,
    val type: TypeRace,
)
