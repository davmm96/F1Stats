package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.repository.FavoriteRaceRepository

class GetAllFavoriteRacesIdsUseCase(
    private val repository: FavoriteRaceRepository
) {
    suspend operator fun invoke(): List<Int> = repository.getAllFavoriteRacesIds()
}
