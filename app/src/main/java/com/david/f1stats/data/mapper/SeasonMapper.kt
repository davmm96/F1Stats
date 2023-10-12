package com.david.f1stats.data.mapper

import com.david.f1stats.domain.model.Season
import javax.inject.Inject

class SeasonMapper @Inject constructor(): IMapper<List<Int>?, List<Season>?> {
    override fun fromMap(from: List<Int>?): List<Season>? {
        return from?.map { seasonData ->
            Season(season = seasonData.toString())
        }?.reversed()
    }
}
