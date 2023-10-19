package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.teamDetail.TeamDetailData
import com.david.f1stats.data.model.teamDetail.TeamDetailHighestRaceFinishData
import com.david.f1stats.domain.model.TeamDetail
import javax.inject.Inject

class TeamDetailMapper @Inject constructor(): IMapper<TeamDetailData, TeamDetail> {

    override fun fromMap(from: TeamDetailData): TeamDetail {
        return from.toTeamDetail()
    }

    private fun TeamDetailData.toTeamDetail() = TeamDetail(
        name = name ?: "Name not found",
        location = base ?: "Country not found",
        image = logo ?: "Image not found",
        firstSeason = first_team_entry ?: "Data not found",
        worldChampionships = world_championships.toString(),
        wins = highest_race_finish?.getWins() ?: "0",
        polePositions = pole_positions.toString(),
        fastestLaps = fastest_laps ?: "Data not found"
    )

    private fun TeamDetailHighestRaceFinishData.getWins(): String {
        return if (position == 1) number.toString() else "0"
    }
}
