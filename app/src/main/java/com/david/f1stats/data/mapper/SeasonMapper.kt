package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.season.SeasonData
import com.david.f1stats.domain.model.Season
import javax.inject.Inject

class SeasonMapper @Inject constructor(): IMapper<List<SeasonData>?, List<Season>?> {
    override fun fromMap(from: List<SeasonData>?): List<Season>? {
        return from?.map { seasonData ->
            Season(season = seasonData.season?.toString() ?: "")
        }
    }
}
