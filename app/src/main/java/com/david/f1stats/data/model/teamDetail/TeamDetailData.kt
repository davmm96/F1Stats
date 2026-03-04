package com.david.f1stats.data.model.teamDetail

data class TeamDetailData(
    val base: String? = "",
    val chassis: String? = "",
    val director: String? = "",
    val engine: String? = "",
    val fastestLaps: String? = "",
    val firstTeamEntry: String? = "",
    val highestRaceFinish: TeamDetailHighestRaceFinishData? = TeamDetailHighestRaceFinishData(number = 0, position = 0),
    val id: Int? = 0,
    val logo: String? = "",
    val name: String? = "",
    val polePositions: Int? = 0,
    val president: String? = "",
    val technicalManager: String? = "",
    val tyres: String? = "",
    val worldChampionships: Int? = 0
)
