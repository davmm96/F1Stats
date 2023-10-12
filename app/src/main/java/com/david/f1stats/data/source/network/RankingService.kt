package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.rankingDriver.RankingDriverData
import com.david.f1stats.data.model.rankingTeam.RankingTeamData
import com.david.f1stats.utils.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class RankingService @Inject constructor(private val api:APIClient, private val preferencesHelper: PreferencesHelper){
    suspend fun getDriversRanking():List<RankingDriverData>{
        return withContext(Dispatchers.IO) {
            val response = api.getCurrentRankingDrivers(preferencesHelper.getSelectedSeason() ?: Calendar.getInstance().get(Calendar.YEAR).toString())
            response.body()?.response ?: emptyList()
        }
    }

    suspend fun getTeamsRanking():List<RankingTeamData>{
        return withContext(Dispatchers.IO) {
            val response = api.getCurrentRankingTeams(preferencesHelper.getSelectedSeason() ?: Calendar.getInstance().get(Calendar.YEAR).toString())
            response.body()?.response ?: emptyList()
        }
    }
}