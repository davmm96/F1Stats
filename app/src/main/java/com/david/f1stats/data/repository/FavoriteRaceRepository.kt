package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.FavoriteRaceMapper
import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.data.source.local.RaceDao
import com.david.f1stats.domain.model.Race
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRaceRepository constructor(
    private val raceDao: RaceDao,
    private val favoriteRaceMapper: FavoriteRaceMapper,
) {
    fun getFavoriteRaces(): Flow<List<FavoriteRace>> {
        return raceDao.getAllFavoriteRaces().map { list ->
            list.sortedWith(compareByDescending<FavoriteRace> { it.season }
                .thenBy { it.competition })
        }
    }

    suspend fun getAllFavoriteRacesIds(): List<Int> {
        return raceDao.getAllFavoriteRaceIds()
    }

    suspend fun insertFavoriteRace(race: Race) {
        raceDao.insertFavoriteRace(favoriteRaceMapper.fromMap(race))
    }

    suspend fun deleteFavoriteRace(raceId: Int) {
        raceDao.deleteFavoriteRace(raceId)
    }
}
