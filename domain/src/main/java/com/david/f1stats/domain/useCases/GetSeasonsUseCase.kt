package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.Season
import com.david.f1stats.domain.repository.SeasonRepository

class GetSeasonsUseCase(
    private val repository: SeasonRepository
) {
    suspend operator fun invoke(): Result<List<Season>> = repository.getSeasons()
}
