package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.season.SeasonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeasonService @Inject constructor(private val api:APIClient){
    suspend fun getSeasons():List<SeasonData>{
        return withContext(Dispatchers.IO) {
            val response = api.getSeasons()
            response.body()?.response ?: emptyList()
        }
    }
}