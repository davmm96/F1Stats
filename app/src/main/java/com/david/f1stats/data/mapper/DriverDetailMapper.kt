package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.driverDetail.DriverDetailData
import com.david.f1stats.data.model.driverDetail.DriverDetailHighestRaceFinishData
import com.david.f1stats.domain.model.DriverDetail
import javax.inject.Inject

class DriverDetailMapper @Inject constructor(): IMapper<DriverDetailData, DriverDetail> {

    override fun fromMap(from: DriverDetailData): DriverDetail {
        return from.toDriverDetail()
    }

    private fun DriverDetailData.toDriverDetail() = DriverDetail(
        name = name,
        nationality = nationality ?: "",
        birthdate = birthdate ?: "",
        points = career_points ?: "No data",
        image = image ?: "",
        country = country?.name?: "",
        number = "#${number}",
        gpEntered = grands_prix_entered?.toString() ?: "No data",
        worldChampionships = world_championships?.toString() ?: "No data",
        podiums = podiums?.toString() ?: "No data",
        wins = highest_race_finish?.wins() ?: "No data",
        teamImage = teams.firstOrNull()?.team?.logo ?: ""
    )

    private fun DriverDetailHighestRaceFinishData.wins(): String {
        return if (position == 1) number.toString() else "0"
    }
}
