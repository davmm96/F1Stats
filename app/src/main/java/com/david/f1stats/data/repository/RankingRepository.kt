package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.RankingDriverMapper
import com.david.f1stats.data.mapper.RankingTeamMapper
import com.david.f1stats.data.source.network.RankingService
import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.domain.model.RankingTeam
import javax.inject.Inject

class RankingRepository @Inject constructor(
    private val api: RankingService,
    private val rankingDriverMapper: RankingDriverMapper,
    private val rankingTeamMapper: RankingTeamMapper
) {

    suspend fun getRankingDriver(): List<RankingDriver>?{
        val response = api.getDriversRanking()
        return rankingDriverMapper.fromMap(response)
    }

    suspend fun getRankingTeam(): List<RankingTeam>?{
        val response = api.getTeamsRanking()
        return rankingTeamMapper.fromMap(response)
    }
}