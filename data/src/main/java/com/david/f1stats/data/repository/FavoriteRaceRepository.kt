package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.FavoriteRaceMapper
import com.david.f1stats.data.model.favoriteRace.FavoriteRace as FavoriteRaceEntity
import com.david.f1stats.data.source.local.RaceDao
import com.david.f1stats.domain.model.FavoriteRace
import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.repository.FavoriteRaceRepository as IFavoriteRaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRaceRepository(
    private val raceDao: RaceDao,
    private val favoriteRaceMapper: FavoriteRaceMapper,
) : IFavoriteRaceRepository {
    override fun getFavoriteRaces(): Flow<List<FavoriteRace>> {
        return raceDao.getAllFavoriteRaces().map { list ->
            list.sortedWith(compareByDescending<FavoriteRaceEntity> { it.season }
                .thenBy { it.competition })
                .map { it.toDomain() }
        }
    }

    override suspend fun getAllFavoriteRacesIds(): List<Int> {
        return raceDao.getAllFavoriteRaceIds()
    }

    override suspend fun insertFavoriteRace(race: Race) {
        raceDao.insertFavoriteRace(favoriteRaceMapper.fromMap(race))
    }

    override suspend fun deleteFavoriteRace(raceId: Int) {
        raceDao.deleteFavoriteRace(raceId)
    }

    private fun FavoriteRaceEntity.toDomain() = FavoriteRace(
        id = id,
        competition = competition,
        country = country,
        season = season,
    )
}
