package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.RaceDetailMapper
import com.david.f1stats.data.mapper.RaceMapper
import com.david.f1stats.data.mapper.RaceResultMapper
import com.david.f1stats.data.source.network.RaceService
import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.RaceResult
import javax.inject.Inject

class RaceRepository @Inject constructor(
    private val raceService: RaceService,
    private val raceMapper: RaceMapper,
    private val raceDetailMapper: RaceDetailMapper,
    private val raceResultMapper: RaceResultMapper
) {
    suspend fun getRaces(): List<Race>{
        val response = raceService.getRaces()
        return raceMapper.fromMap(response) ?: emptyList()
    }

    suspend fun getRaceDetails(id: Int): List<RaceDetail> {
        val response = raceService.getRaceDetails(id)
        return raceDetailMapper.fromMap(response) ?: emptyList()
    }

    suspend fun getCompletedRaces(): List<Race> {
        val response = raceService.getCompletedRaces()
        return raceMapper.fromMap(response) ?: emptyList()
    }

    suspend fun getRaceResult(id: Int): List<RaceResult> {
        val response = raceService.getRaceResult(id)
        return raceResultMapper.fromMap(response) ?: emptyList()
    }
}
