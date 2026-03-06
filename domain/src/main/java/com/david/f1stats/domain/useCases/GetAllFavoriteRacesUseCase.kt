package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.FavoriteRace
import com.david.f1stats.domain.repository.FavoriteRaceRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoriteRacesUseCase(
    private val repository: FavoriteRaceRepository
) {
    operator fun invoke(): Flow<List<FavoriteRace>> = repository.getFavoriteRaces()
}
