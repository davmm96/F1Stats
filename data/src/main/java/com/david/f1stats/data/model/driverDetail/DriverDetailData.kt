package com.david.f1stats.data.model.driverDetail

import com.google.gson.annotations.SerializedName

data class DriverDetailData(
    val abbr: String = "",
    val birthdate: String? = "",
    val birthplace: String? = "",
    @SerializedName("career_points")
    val careerPoints: String? = "",
    val country: DriverDetailCountryData? = DriverDetailCountryData("", ""),
    @SerializedName("grands_prix_entered")
    val grandsPrixEntered: Int? = 0,
    @SerializedName("highest_grid_position")
    val highestGridPosition: Int? = 0,
    @SerializedName("highest_race_finish")
    val highestRaceFinish: DriverDetailHighestRaceFinishData? = DriverDetailHighestRaceFinishData(
        0,
        0
    ),
    val id: Int = 0,
    val image: String? = "",
    val name: String = "",
    val nationality: String? = "",
    val number: Int? = 0,
    val podiums: Int? = 0,
    val teams: List<DriverDetailTeamItemData> = listOf(),
    @SerializedName("world_championships")
    val worldChampionships: Int? = 0,
)
