package com.david.f1stats.data.repository

import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.data.source.local.RaceProvider
import com.david.f1stats.data.source.network.RaceService
import javax.inject.Inject

class RaceRepository @Inject constructor(private val api: RaceService, private val raceProvider: RaceProvider){
    suspend fun getRaces(): List<RaceData>{
        val response = api.getRaces()
        raceProvider.races = response
        return response
    }
}
