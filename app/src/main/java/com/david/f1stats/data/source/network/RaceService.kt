package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.race.RaceData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RaceService @Inject constructor(private val api:RaceAPIClient){
    suspend fun getRaces():List<RaceData>{
        return withContext(Dispatchers.IO) {
            val response = api.getCurrentRaces()
            response.body()?.response ?: emptyList()
        }
    }
}
