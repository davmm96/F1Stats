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
        name = name ?: "No data",
        location = base ?: "",
        image = logo ?: "",
        firstSeason = first_team_entry ?: "No data",
        worldChampionships = world_championships?.toString() ?: "No data",
        wins = highest_race_finish?.getWins() ?: "No data",
        polePositions = pole_positions?.toString() ?: "No data",
        fastestLaps = fastest_laps ?: "No data"
    )

    private fun TeamDetailHighestRaceFinishData.getWins(): String {
        return if (position == 1) number.toString() else "0"
    }
}
