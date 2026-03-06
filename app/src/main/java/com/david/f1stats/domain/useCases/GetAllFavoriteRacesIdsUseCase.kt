package com.david.f1stats.domain.useCases

import com.david.f1stats.data.repository.FavoriteRaceRepository

class GetAllFavoriteRacesIdsUseCase constructor(
    private val repository: FavoriteRaceRepository
) {
    suspend operator fun invoke(): List<Int> = repository.getAllFavoriteRacesIds()
}
