package com.david.f1stats.data.model.raceResult

data class RaceResultData(
    val driver: RaceResultDriverData,
    val gap: String?,
    val grid: String?,
    val laps: Int?,
    val pits: Int?,
    val position: Int,
    val race: RaceResultRaceData,
    val team: RaceResultTeamData,
    val time: String?
)
