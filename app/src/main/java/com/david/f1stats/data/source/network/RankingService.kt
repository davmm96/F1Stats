package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.rankingDriver.RankingDriverData
import com.david.f1stats.data.model.rankingTeam.RankingTeamData
import com.david.f1stats.utils.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import com.david.f1stats.data.model.base.Result

class RankingService @Inject constructor(
    private val api:APIClient,
    private val preferencesHelper: PreferencesHelper){

    suspend fun getDriversRanking(): Result<List<RankingDriverData>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getRankingDrivers(getSelectedSeasonOrDefault())
                if(response.isSuccessful)
                    Result.Success( response.body()?.response ?: emptyList())
                else
                    Result.Error(Exception(response.message()))
            }
            catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun getTeamsRanking(): Result<List<RankingTeamData>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getRankingTeams(getSelectedSeasonOrDefault())
                if(response.isSuccessful)
                    Result.Success( response.body()?.response ?: emptyList())
                else
                    Result.Error(Exception(response.message()))
            }
            catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    private fun getSelectedSeasonOrDefault(): String {
        return preferencesHelper.selectedSeason ?: Calendar.getInstance().get(Calendar.YEAR).toString()
    }
}
