package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.SeasonMapper
import com.david.f1stats.data.source.network.SeasonService
import com.david.f1stats.domain.model.Season
import javax.inject.Inject

class SeasonRepository @Inject constructor(
    private val seasonService: SeasonService,
    private val seasonMapper: SeasonMapper,
){
    suspend fun getSeasons(): List<Season>{
        val response = seasonService.getSeasons()
        return seasonMapper.fromMap(response) ?: emptyList()
    }
}