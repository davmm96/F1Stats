package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.repository.FavoriteRaceRepository

class DeleteFavoriteUseCase(
    private val repository: FavoriteRaceRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteFavoriteRace(id)
}
