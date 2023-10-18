package com.david.f1stats.data.model.teamDetail

data class TeamDetailData(
    val base: String? = "",
    val chassis: String? = "",
    val director: String? = "",
    val engine: String? = "",
    val fastest_laps: String? = "",
    val first_team_entry: String? = "",
    val highest_race_finish: TeamDetailHighestRaceFinishData? = TeamDetailHighestRaceFinishData(number = 0, position = 0),
    val id: Int? = 0,
    val logo: String? = "",
    val name: String? = "",
    val pole_positions: Int? = 0,
    val president: String? = "",
    val technical_manager: String? = "",
    val tyres: String? = "",
    val world_championships: Int? = 0
)
