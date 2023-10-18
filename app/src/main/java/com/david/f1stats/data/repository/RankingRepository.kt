package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.RankingDriverMapper
import com.david.f1stats.data.mapper.RankingTeamMapper
import com.david.f1stats.data.source.network.RankingService
import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.domain.model.RankingTeam
import javax.inject.Inject

class RankingRepository @Inject constructor(
    private val rankingService: RankingService,
    private val rankingDriverMapper: RankingDriverMapper,
    private val rankingTeamMapper: RankingTeamMapper
) {

    suspend fun getRankingDriver(): List<RankingDriver>{
        val response = rankingService.getDriversRanking()
        return rankingDriverMapper.fromMap(response) ?: emptyList()
    }

    suspend fun getRankingTeam(): List<RankingTeam>{
        val response = rankingService.getTeamsRanking()
        return rankingTeamMapper.fromMap(response) ?: emptyList()
    }
}