package com.david.f1stats.data.model.race

import com.google.gson.annotations.SerializedName

data class RaceData(
    val circuit: RaceCircuitData,
    val competition: RaceCompetitionData,
    val date: String,
    val distance: String,
    @SerializedName("fastest_lap")
    val fastestLap: RaceFastestLapData,
    val id: Int,
    val laps: RaceLapsData,
    val season: Int,
    val status: String,
    val timezone: String,
    val type: String,
    val weather: Any
)
