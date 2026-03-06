package com.david.f1stats.domain.useCases

import com.david.f1stats.data.repository.FavoriteRaceRepository

class DeleteFavoriteUseCase constructor(
    private val repository: FavoriteRaceRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteFavoriteRace(id)
}
