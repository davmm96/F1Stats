package com.david.f1stats.data.source.network

import com.david.f1stats.data.RetrofitHelper
import com.david.f1stats.data.model.race.RaceData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RaceService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getRaces():List<RaceData>{
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(RaceAPIClient::class.java).getCurrentRaces()
            response.body()?.response ?: emptyList()
        }
    }
}
