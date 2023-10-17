package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.driverDetail.DriverDetailData
import com.david.f1stats.data.model.driverDetail.DriverDetailHighestRaceFinishData
import com.david.f1stats.domain.model.DriverDetail
import javax.inject.Inject

class DriverDetailMapper @Inject constructor(): IMapper<DriverDetailData, DriverDetail> {
    override fun fromMap(from: DriverDetailData): DriverDetail {
        return DriverDetail(
            name = from.name,
            nationality = from.nationality,
            birthdate = from.birthdate,
            points = from.career_points,
            image = from.image,
            country = from.country.name?: "Not found",
            number = "#${from.number}",
            gpEntered = from.grands_prix_entered.toString(),
            worldChampionships = from.world_championships.toString(),
            podiums = from.podiums.toString(),
            wins = from.highest_race_finish.wins(),
            teamImage = from.teams.firstOrNull()?.team?.logo ?: "Team logo not found"
        )
    }

    private fun DriverDetailHighestRaceFinishData.wins(): String {
        return if (position == 1) number.toString() else "0"
    }
}