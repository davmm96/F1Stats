package com.david.f1stats.data.model.teamDetail

data class Response(
    val base: Any,
    val chassis: Any,
    val director: String,
    val engine: String,
    val fastest_laps: Any,
    val first_team_entry: Any,
    val highest_race_finish: HighestRaceFinish,
    val id: Int,
    val logo: String,
    val name: String,
    val pole_positions: Any,
    val president: String,
    val technical_manager: String,
    val tyres: String,
    val world_championships: Any
)