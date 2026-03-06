package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.SeasonMapper
import com.david.f1stats.data.source.network.SeasonService
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.Season
import com.david.f1stats.domain.repository.SeasonRepository as ISeasonRepository

class SeasonRepository(
    private val seasonService: SeasonService,
    private val seasonMapper: SeasonMapper,
) : ISeasonRepository {
    override suspend fun getSeasons(): Result<List<Season>> {
        return when (val response = seasonService.getSeasons()) {
            is Result.Success -> {
                Result.Success(seasonMapper.fromMap(response.data) ?: emptyList())
            }

            is Result.Error -> {
                response
            }
        }
    }
}
