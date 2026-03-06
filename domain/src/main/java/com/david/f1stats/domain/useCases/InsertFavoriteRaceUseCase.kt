package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.repository.FavoriteRaceRepository

class InsertFavoriteRaceUseCase(
    private val repository: FavoriteRaceRepository
) {
    suspend operator fun invoke(race: Race) = repository.insertFavoriteRace(race)
}
