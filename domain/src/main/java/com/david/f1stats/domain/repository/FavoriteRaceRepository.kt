package com.david.f1stats.domain.repository

import com.david.f1stats.domain.model.FavoriteRace
import com.david.f1stats.domain.model.Race
import kotlinx.coroutines.flow.Flow

interface FavoriteRaceRepository {
    fun getFavoriteRaces(): Flow<List<FavoriteRace>>
    suspend fun getAllFavoriteRacesIds(): List<Int>
    suspend fun insertFavoriteRace(race: Race)
    suspend fun deleteFavoriteRace(raceId: Int)
}
