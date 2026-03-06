package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.domain.model.Race

class FavoriteRaceMapper : IMapper<Race, FavoriteRace> {
    override fun fromMap(from: Race): FavoriteRace {
        return FavoriteRace(
            id = from.idRace,
            competition = from.competition,
            country = from.country,
            season = from.season
        )
    }
}
