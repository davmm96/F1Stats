package com.david.f1stats.domain.useCases

import com.david.f1stats.data.model.base.Result
import com.david.f1stats.data.repository.RaceRepository
import com.david.f1stats.domain.model.Race
import javax.inject.Inject

class GetRacesUseCase @Inject constructor(
    private val repository: RaceRepository) {
    suspend operator fun invoke(): Result<List<Race>> = repository.getRaces()
}
