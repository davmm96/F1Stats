package com.david.f1stats.data.source.network

import com.david.f1stats.data.RetrofitHelper
import com.david.f1stats.data.model.race.RaceData

class RaceService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getRaces():List<RaceData>{
       val response = retrofit.create(RaceAPIClient::class.java).getRaces()
        return    response.body()?.response ?: emptyList()

    }
}
