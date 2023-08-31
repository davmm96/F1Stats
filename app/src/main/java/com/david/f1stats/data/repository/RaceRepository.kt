package com.david.f1stats.data.repository

import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.data.source.local.RaceProvider
import com.david.f1stats.data.source.network.RaceService

class RaceRepository {
    private val api = RaceService()

    suspend fun getRaces(): List<RaceData>{
        val response = api.getRaces()
        RaceProvider.races = response
        return response
    }
}
