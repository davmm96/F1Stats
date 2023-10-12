package com.david.f1stats.domain

import com.david.f1stats.data.repository.SeasonRepository
import com.david.f1stats.domain.model.Season
import javax.inject.Inject

class GetSeasonsUseCase @Inject constructor(private val repository: SeasonRepository) {
    suspend operator fun invoke(): List<Season>? = repository.getSeasons()
}