package com.david.f1stats.data.model.teamDetail

import com.google.gson.annotations.SerializedName

data class TeamDetailData(
    val base: String? = "",
    val chassis: String? = "",
    val director: String? = "",
    val engine: String? = "",
    @SerializedName("fastest_laps")
    val fastestLaps: String? = "",
    @SerializedName("first_team_entry")
    val firstTeamEntry: String? = "",
    @SerializedName("highest_race_finish")
    val highestRaceFinish: TeamDetailHighestRaceFinishData? = TeamDetailHighestRaceFinishData(number = 0, position = 0),
    val id: Int? = 0,
    val logo: String? = "",
    val name: String? = "",
    @SerializedName("pole_positions")
    val polePositions: Int? = 0,
    val president: String? = "",
    @SerializedName("technical_manager")
    val technicalManager: String? = "",
    val tyres: String? = "",
    @SerializedName("world_championships")
    val worldChampionships: Int? = 0
)
