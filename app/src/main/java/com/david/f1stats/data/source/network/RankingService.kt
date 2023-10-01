package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.rankingDriver.RankingDriverData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RankingService @Inject constructor(private val api:APIClient){
    suspend fun getDriversRanking():List<RankingDriverData>{
        return withContext(Dispatchers.IO) {
            val response = api.getCurrentRankingDrivers()
            response.body()?.response ?: emptyList()
        }
    }
}