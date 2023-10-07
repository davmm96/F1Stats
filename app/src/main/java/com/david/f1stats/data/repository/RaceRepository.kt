package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.RaceDetailMapper
import com.david.f1stats.data.mapper.RaceMapper
import com.david.f1stats.data.source.local.RaceProvider
import com.david.f1stats.data.source.network.RaceService
import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.model.RaceDetail
import javax.inject.Inject

class RaceRepository @Inject constructor(
    private val api: RaceService,
    private val raceProvider: RaceProvider,
    private val raceMapper: RaceMapper,
    private val raceDetailMapper: RaceDetailMapper){

    suspend fun getRaces(): List<Race>?{
        val response = api.getRaces()
        raceProvider.races = raceMapper.fromMap(response)
        return raceMapper.fromMap(response)
    }

    suspend fun getRaceDetails(id: Int): List<RaceDetail>? {
        val response = api.getRaceDetails(id)
        return raceDetailMapper.fromMap(response)
    }

    suspend fun getCompletedRaces(): List<Race>? {
        val response = api.getCompletedRaces()
        return raceMapper.fromMap(response)
    }
}
