package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.domain.model.RaceDetail
import javax.inject.Inject

class FavoriteRaceMapper @Inject constructor(): IMapper<RaceDetail, FavoriteRace> {
    override fun fromMap(from: RaceDetail): FavoriteRace {
        return FavoriteRace(
            id = from.id,
            competition = from.competition,
            dayInterval = from.day,
            month = from.month,
            country = from.country,
            laps = from.laps
        )
    }
}