package com.david.f1stats.domain.useCases

import com.david.f1stats.data.model.base.Result
import com.david.f1stats.data.repository.SeasonRepository
import com.david.f1stats.domain.model.Season

class GetSeasonsUseCase constructor(
    private val repository: SeasonRepository
) {
    suspend operator fun invoke(): Result<List<Season>> = repository.getSeasons()
}
