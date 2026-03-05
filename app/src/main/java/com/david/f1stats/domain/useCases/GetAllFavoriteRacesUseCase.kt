package com.david.f1stats.domain.useCases

import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.data.repository.FavoriteRaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteRacesUseCase @Inject constructor(
    private val repository: FavoriteRaceRepository
) {
    operator fun invoke(): Flow<List<FavoriteRace>> = repository.getFavoriteRaces()
}
