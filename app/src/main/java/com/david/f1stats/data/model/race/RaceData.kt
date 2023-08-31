package com.david.f1stats.data.model.race

data class RaceData(
    val circuit: RaceCircuitData,
    val competition: RaceCompetitionData,
    val date: String,
    val distance: String,
    val fastest_lap: RaceFastestLapData,
    val id: Int,
    val laps: RaceLapsData,
    val season: Int,
    val status: String,
    val timezone: String,
    val type: String,
    val weather: Any
)
