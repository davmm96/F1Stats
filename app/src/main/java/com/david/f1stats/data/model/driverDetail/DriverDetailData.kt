package com.david.f1stats.data.model.driverDetail

data class DriverDetailData(
    val abbr: String = "",
    val birthdate: String? = "",
    val birthplace: String? = "",
    val careerPoints: String? = "",
    val country: DriverDetailCountryData? = DriverDetailCountryData("", ""),
    val grandsPrixEntered: Int? = 0,
    val highestGridPosition: Int? = 0,
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
    val worldChampionships: Int? = 0,
)
