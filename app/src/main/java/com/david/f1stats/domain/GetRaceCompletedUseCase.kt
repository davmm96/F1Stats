package com.david.f1stats.domain

import com.david.f1stats.data.repository.RaceRepository
import com.david.f1stats.domain.model.Race
import javax.inject.Inject

class GetRaceCompletedUseCase @Inject constructor(private val repository: RaceRepository){
    suspend operator fun invoke(): List<Race>? = repository.getCompletedRaces()
}