package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.RaceResult
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.RaceRepository

class GetRaceResultUseCase(
    private val repository: RaceRepository
) {
    suspend operator fun invoke(id: Int): Result<List<RaceResult>> = repository.getRaceResult(id)
}
