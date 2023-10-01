package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.rankingDriver.RankingDriverData
import com.david.f1stats.data.model.rankingTeam.RankingTeamData
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

    suspend fun getTeamsRanking():List<RankingTeamData>{
        return withContext(Dispatchers.IO) {
            val response = api.getCurrentRankingTeams()
            response.body()?.response ?: emptyList()
        }
    }
}