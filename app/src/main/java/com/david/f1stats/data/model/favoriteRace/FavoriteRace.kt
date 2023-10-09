package com.david.f1stats.data.model.favoriteRace

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_races")
data class FavoriteRace (
    @PrimaryKey
    val id: Int,
    val competition: String,
    val dayInterval: String,
    val month: String,
    val country: String,
    val laps: String,
)