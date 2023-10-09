package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.FavoriteRaceMapper
import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.data.source.local.db.RaceDao
import com.david.f1stats.domain.model.RaceDetail
import javax.inject.Inject

class FavoriteRaceRepository @Inject constructor(
    private val raceDao: RaceDao,
    private val favoriteRaceMapper: FavoriteRaceMapper,
) {
    suspend  fun getFavoriteRaces(): List<FavoriteRace> {
        return raceDao.getAllFavoriteRaces()
    }

    suspend  fun getFavoriteRaceById(id: Int): FavoriteRace? {
        return raceDao.getFavoriteRaceById(id)
    }

    suspend fun insertFavoriteRace(race: RaceDetail) {
        raceDao.insertFavoriteRace(favoriteRaceMapper.fromMap(race))
    }

    suspend fun deleteFavoriteRace(raceId: Int) {
        raceDao.deleteFavoriteRace(raceId)
    }
}