package com.david.f1stats.domain

import com.david.f1stats.data.repository.FavoriteRaceRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(private val repository: FavoriteRaceRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteFavoriteRace(id)
}