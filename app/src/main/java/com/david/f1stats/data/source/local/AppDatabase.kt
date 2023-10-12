package com.david.f1stats.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.david.f1stats.data.model.favoriteRace.FavoriteRace

@Database(entities = [FavoriteRace::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun raceDao(): RaceDao
}
