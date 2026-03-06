package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.RaceRepository

class GetRaceCompletedUseCase(
    private val repository: RaceRepository
) {
    suspend operator fun invoke(): Result<List<Race>> = repository.getCompletedRaces()
}
