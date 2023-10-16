package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.FavoriteRaceMapper
import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.data.source.local.RaceDao
import com.david.f1stats.domain.model.Race
import javax.inject.Inject

class FavoriteRaceRepository @Inject constructor(
    private val raceDao: RaceDao,
    private val favoriteRaceMapper: FavoriteRaceMapper,
) {
    suspend  fun getFavoriteRaces(): List<FavoriteRace> {
        return raceDao.getAllFavoriteRaces()
            .sortedWith(compareByDescending<FavoriteRace> { it.season }
                .thenBy { it.competition })
    }

    suspend fun getAllFavoriteRacesIds(): List<Int> {
        return raceDao.getAllFavoriteRaceIds()
    }

    suspend  fun getFavoriteRaceById(id: Int): FavoriteRace? {
        return raceDao.getFavoriteRaceById(id)
    }

    suspend fun insertFavoriteRace(race: Race) {
        raceDao.insertFavoriteRace(favoriteRaceMapper.fromMap(race))
    }

    suspend fun deleteFavoriteRace(raceId: Int) {
        raceDao.deleteFavoriteRace(raceId)
    }
}