package com.david.f1stats.data.source.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.david.f1stats.data.model.favoriteRace.FavoriteRace

@Dao
interface RaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRace(race: FavoriteRace): Long

    @Query("SELECT * FROM favorite_races")
    suspend fun getAllFavoriteRaces(): List<FavoriteRace>

    @Query("SELECT * FROM favorite_races WHERE id = :raceId")
    suspend fun getFavoriteRaceById(raceId: Int): FavoriteRace

    @Delete
    suspend fun deleteFavoriteRace(race: FavoriteRace)
}
