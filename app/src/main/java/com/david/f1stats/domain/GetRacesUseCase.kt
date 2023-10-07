package com.david.f1stats.domain

import com.david.f1stats.data.repository.RaceRepository
import com.david.f1stats.data.source.local.RaceProvider
import com.david.f1stats.domain.model.Race
import javax.inject.Inject

class GetRacesUseCase @Inject constructor(private val repository: RaceRepository, private val raceProvider: RaceProvider) {

    suspend operator fun invoke(): List<Race>?{
        val races = raceProvider.races

        return if(races.isNullOrEmpty()){
            repository.getRaces()
        } else
            races
    }
}
