package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.RankingDriverMapper
import com.david.f1stats.data.source.network.RankingService
import com.david.f1stats.domain.model.RankingDriver
import javax.inject.Inject

class RankingRepository @Inject constructor(
    private val api: RankingService,
    private val rankingDriverMapper: RankingDriverMapper) {

    suspend fun getRankingDriver(): List<RankingDriver>?{
        val response = api.getDriversRanking()
        return rankingDriverMapper.fromMap(response)
    }
}