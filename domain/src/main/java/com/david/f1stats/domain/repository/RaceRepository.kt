package com.david.f1stats.domain.repository

import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.RaceResult
import com.david.f1stats.domain.model.Result

interface RaceRepository {
    suspend fun getRaces(): Result<List<Race>>
    suspend fun getRaceDetails(id: Int): Result<List<RaceDetail>>
    suspend fun getCompletedRaces(): Result<List<Race>>
    suspend fun getRaceResult(id: Int): Result<List<RaceResult>>
}
