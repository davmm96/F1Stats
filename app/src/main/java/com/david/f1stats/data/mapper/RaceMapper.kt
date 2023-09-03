package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.domain.model.Race
import com.david.f1stats.utils.Constants.FORMAT_DAY
import com.david.f1stats.utils.Constants.FORMAT_MONTH
import com.david.f1stats.utils.formatDate
import javax.inject.Inject

class RaceMapper @Inject constructor(): IMapper<List<RaceData>?, List<Race>?> {
    override fun fromMap(from: List<RaceData>?): List<Race>? {
        return from?.map { raceData ->
            Race(
                competition = raceData.competition.name,
                day = formatDate(raceData.date, FORMAT_DAY),
                month = formatDate(raceData.date, FORMAT_MONTH).replaceFirstChar  { it.uppercaseChar() },
                country = raceData.competition.location.country,
                id = raceData.competition.id,
                laps = raceData.laps.total.toString() + " laps"
            )
        }
    }
}
