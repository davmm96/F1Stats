package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.RaceDetailMapper
import com.david.f1stats.data.mapper.RaceMapper
import com.david.f1stats.data.mapper.RaceResultMapper
import com.david.f1stats.data.source.network.RaceService
import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.RaceResult
import com.david.f1stats.data.model.base.Result
import javax.inject.Inject

class RaceRepository @Inject constructor(
    private val raceService: RaceService,
    private val raceMapper: RaceMapper,
    private val raceDetailMapper: RaceDetailMapper,
    private val raceResultMapper: RaceResultMapper
) {
    suspend fun getRaces(): Result<List<Race>>{
        return when (val response = raceService.getRaces()) {
            is Result.Success -> {
                Result.Success(raceMapper.fromMap(response.data) ?: emptyList())
            }
            is Result.Error -> {
                response
            }
        }
    }

    suspend fun getRaceDetails(id: Int): Result<List<RaceDetail>> {
        return when (val response = raceService.getRaceDetails(id)) {
            is Result.Success -> {
                Result.Success(raceDetailMapper.fromMap(response.data) ?: emptyList())
            }
            is Result.Error -> {
                response
            }
        }
    }

    suspend fun getCompletedRaces(): Result<List<Race>> {
        return when (val response = raceService.getCompletedRaces()) {
            is Result.Success -> {
                Result.Success(raceMapper.fromMap(response.data) ?: emptyList())
            }
            is Result.Error -> {
                response
            }
        }
    }

    suspend fun getRaceResult(id: Int): Result<List<RaceResult>> {
        return when (val response = raceService.getRaceResult(id)) {
            is Result.Success -> {
                Result.Success(raceResultMapper.fromMap(response.data) ?: emptyList())
            }
            is Result.Error -> {
                response
            }
        }
    }
}
