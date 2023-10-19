package com.david.f1stats.data.model.driverDetail

data class DriverDetailData(
    val abbr: String = "",
    val birthdate: String? = "",
    val birthplace: String?  = "",
    val career_points: String? = "",
    val country: DriverDetailCountryData? = DriverDetailCountryData("", ""),
    val grands_prix_entered: Int? = 0,
    val highest_grid_position: Int? = 0,
    val highest_race_finish: DriverDetailHighestRaceFinishData? = DriverDetailHighestRaceFinishData(0, 0),
    val id: Int = 0,
    val image: String? = "",
    val name: String = "",
    val nationality: String? = "",
    val number: Int? = 0,
    val podiums: Int? = 0,
    val teams: List<DriverDetailTeamItemData> = listOf(),
    val world_championships: Int? = 0,
)
