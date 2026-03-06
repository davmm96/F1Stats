package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.TeamDetailMapper
import com.david.f1stats.data.source.network.TeamService
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.TeamDetail
import com.david.f1stats.domain.repository.TeamRepository as ITeamRepository

class TeamRepository(
    private val teamService: TeamService,
    private val teamDetailMapper: TeamDetailMapper,
) : ITeamRepository {
    override suspend fun getTeamDetail(id: Int): Result<TeamDetail> {
        return when (val response = teamService.getTeamDetail(id)) {
            is Result.Success -> {
                Result.Success(teamDetailMapper.fromMap(response.data))
            }

            is Result.Error -> {
                response
            }
        }
    }
}
