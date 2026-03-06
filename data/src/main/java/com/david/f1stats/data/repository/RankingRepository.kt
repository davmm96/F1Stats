package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.RankingDriverMapper
import com.david.f1stats.data.mapper.RankingTeamMapper
import com.david.f1stats.data.source.network.RankingService
import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.domain.model.RankingTeam
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.RankingRepository as IRankingRepository

class RankingRepository(
    private val rankingService: RankingService,
    private val rankingDriverMapper: RankingDriverMapper,
    private val rankingTeamMapper: RankingTeamMapper
) : IRankingRepository {

    override suspend fun getRankingDriver(): Result<List<RankingDriver>> {
        return when (val response = rankingService.getDriversRanking()) {
            is Result.Success -> {
                Result.Success(rankingDriverMapper.fromMap(response.data) ?: emptyList())
            }

            is Result.Error -> {
                response
            }
        }
    }

    override suspend fun getRankingTeam(): Result<List<RankingTeam>> {
        return when (val response = rankingService.getTeamsRanking()) {
            is Result.Success -> {
                Result.Success(rankingTeamMapper.fromMap(response.data) ?: emptyList())
            }

            is Result.Error -> {
                response
            }
        }
    }
}
