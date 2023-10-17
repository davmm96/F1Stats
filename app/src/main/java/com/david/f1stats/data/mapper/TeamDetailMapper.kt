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
        name = name ?: "Not found",
        location = base ?: "Not found",
        image = logo ?: "Not found",
        firstSeason = first_team_entry ?: "Not found",
        worldChampionships = world_championships.toString(),
        wins = highest_race_finish?.getWins() ?: "0",
        polePositions = pole_positions.toString(),
        fastestLaps = fastest_laps ?: "Not found"
    )

    private fun TeamDetailHighestRaceFinishData.getWins(): String {
        return if (position == 1) number.toString() else "0"
    }
}