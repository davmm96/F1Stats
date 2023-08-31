package com.david.f1stats.data.model.race

data class RaceData(
    val circuit: Circuit,
    val competition: Competition,
    val date: String,
    val distance: String,
    val fastest_lap: FastestLap,
    val id: Int,
    val laps: Laps,
    val season: Int,
    val status: String,
    val timezone: String,
    val type: String,
    val weather: Any
)
