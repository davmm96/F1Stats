package com.david.f1stats.domain

import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.data.repository.FavoriteRaceRepository
import javax.inject.Inject

class GetAllFavoriteRacesUseCase @Inject constructor(private val repository: FavoriteRaceRepository) {
    suspend operator fun invoke(): List<FavoriteRace> = repository.getFavoriteRaces()
}
