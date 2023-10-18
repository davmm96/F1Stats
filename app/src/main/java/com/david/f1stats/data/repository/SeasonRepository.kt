package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.SeasonMapper
import com.david.f1stats.data.source.network.SeasonService
import com.david.f1stats.domain.model.Season
import javax.inject.Inject
import com.david.f1stats.data.model.base.Result

class SeasonRepository @Inject constructor(
    private val seasonService: SeasonService,
    private val seasonMapper: SeasonMapper,
){
    suspend fun getSeasons(): Result<List<Season>>{
        return when (val response =  seasonService.getSeasons()) {
            is Result.Success -> {
                Result.Success(seasonMapper.fromMap(response.data) ?: emptyList())
            }
            is Result.Error -> {
                response
            }
        }
    }
}
