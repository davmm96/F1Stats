package com.david.f1stats.domain

import com.david.f1stats.data.repository.FavoriteRaceRepository
import com.david.f1stats.domain.model.RaceDetail
import javax.inject.Inject

class InsertFavoriteRaceUseCase @Inject constructor(private val repository: FavoriteRaceRepository) {
    suspend operator fun invoke(race: RaceDetail) = repository.insertFavoriteRace(race)
}