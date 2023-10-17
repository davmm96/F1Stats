package com.david.f1stats.domain.useCases

import com.david.f1stats.data.repository.FavoriteRaceRepository
import com.david.f1stats.domain.model.Race
import javax.inject.Inject

class InsertFavoriteRaceUseCase @Inject constructor(
    private val repository: FavoriteRaceRepository) {
    suspend operator fun invoke(race: Race) = repository.insertFavoriteRace(race)
}
