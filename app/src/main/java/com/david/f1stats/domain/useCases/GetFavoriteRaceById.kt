package com.david.f1stats.domain.useCases

import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.data.repository.FavoriteRaceRepository
import javax.inject.Inject

class GetFavoriteRaceById @Inject constructor(
    private val repository: FavoriteRaceRepository) {
    suspend operator fun invoke(raceId: Int): FavoriteRace? = repository.getFavoriteRaceById(raceId)
}
