package com.david.f1stats.data.source.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.david.f1stats.data.model.base.Result

class SeasonService @Inject constructor(private val api:APIClient){
    suspend fun getSeasons(): Result<List<Int>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getSeasons()
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
}