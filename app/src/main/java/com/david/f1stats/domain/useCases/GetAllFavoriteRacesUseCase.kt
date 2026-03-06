package com.david.f1stats.domain.useCases

import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.data.repository.FavoriteRaceRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoriteRacesUseCase constructor(
    private val repository: FavoriteRaceRepository
) {
    operator fun invoke(): Flow<List<FavoriteRace>> = repository.getFavoriteRaces()
}
