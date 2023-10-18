package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.base.Result
import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.data.model.raceResult.RaceResultData
import com.david.f1stats.utils.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class RaceService @Inject constructor(
    private val api:APIClient,
    private val preferencesHelper: PreferencesHelper){

    suspend fun getRaces(): Result<List<RaceData>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getNextRaces(getSelectedSeasonOrDefault())
                if(response.isSuccessful)
                    Result.Success(response.body()?.response ?: emptyList())
                else
                    Result.Error(Exception(response.message()))
            }
            catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun getRaceDetails(id: Int): Result<List<RaceData>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getRaceDetails(id, getSelectedSeasonOrDefault())
                if(response.isSuccessful)
                    Result.Success(response.body()?.response ?: emptyList())
                else
                    Result.Error(Exception(response.message()))
            }
            catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun getCompletedRaces(): Result<List<RaceData>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getCompletedRaces(getSelectedSeasonOrDefault())
                if(response.isSuccessful)
                    Result.Success(response.body()?.response ?: emptyList())
                else
                    Result.Error(Exception(response.message()))
            }
            catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun getRaceResult(id: Int): Result<List<RaceResultData>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getRaceResult(id)
                if(response.isSuccessful)
                    Result.Success(response.body()?.response ?: emptyList())
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
