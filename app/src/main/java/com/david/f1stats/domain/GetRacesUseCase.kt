package com.david.f1stats.domain

import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.data.repository.RaceRepository

class GetRacesUseCase {
    private val repository = RaceRepository()

    suspend operator fun invoke(): List<RaceData> = repository.getRaces()
}
