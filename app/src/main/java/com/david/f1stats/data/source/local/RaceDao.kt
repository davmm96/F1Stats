package com.david.f1stats.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRace(race: FavoriteRace): Long

    @Query("SELECT * FROM favorite_races")
    fun getAllFavoriteRaces(): Flow<List<FavoriteRace>>

    @Query("SELECT * FROM favorite_races WHERE id = :raceId")
    suspend fun getFavoriteRaceById(raceId: Int): FavoriteRace?

    @Query("SELECT id FROM favorite_races")
    suspend fun getAllFavoriteRaceIds(): List<Int>

    @Query("DELETE FROM favorite_races WHERE id = :raceId")
    suspend fun deleteFavoriteRace(raceId: Int)
}
