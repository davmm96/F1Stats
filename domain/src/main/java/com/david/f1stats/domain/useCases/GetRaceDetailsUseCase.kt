package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.RaceRepository

class GetRaceDetailsUseCase(
    private val repository: RaceRepository
) {
    suspend operator fun invoke(id: Int): Result<List<RaceDetail>> = repository.getRaceDetails(id)
}
