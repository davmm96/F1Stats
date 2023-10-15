package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.teamDetail.TeamDetailData
import com.david.f1stats.data.model.teamDetail.TeamDetailHighestRaceFinishData
import com.david.f1stats.domain.model.TeamDetail
import javax.inject.Inject

class TeamDetailMapper @Inject constructor(): IMapper<TeamDetailData, TeamDetail> {
    override fun fromMap(from: TeamDetailData): TeamDetail {
        return TeamDetail(
            name = from.name?:"Not found",
            location = from.base ?: "Not found",
            image = from.logo?:"Not found",
            firstSeason = from.first_team_entry?:"Not found",
            worldChampionships = from.world_championships.toString(),
            wins = getWins(from.highest_race_finish?: TeamDetailHighestRaceFinishData(0, 0)),
            polePositions = from.pole_positions.toString(),
            fastestLaps = from.fastest_laps?:"Not found",
        )
    }

    private fun getWins(highestRaceFinish: TeamDetailHighestRaceFinishData): String{
        return if(highestRaceFinish.position == 1) {
            highestRaceFinish.number.toString()
        } else {
            "0"
        }
    }
}