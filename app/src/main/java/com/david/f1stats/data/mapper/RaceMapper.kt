package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.domain.model.Race
import com.david.f1stats.utils.Constants.FORMAT_MONTH
import com.david.f1stats.utils.formatDate
import com.david.f1stats.utils.formatIntervalDate
import javax.inject.Inject

class RaceMapper @Inject constructor(): IMapper<List<RaceData>?, List<Race>?> {
    override fun fromMap(from: List<RaceData>?): List<Race>? {
        return from?.map { raceData ->
            Race(
                competition = raceData.competition.name,
                dayInterval = formatIntervalDate(raceData.date, 2),
                month = formatDate(raceData.date, FORMAT_MONTH).replaceFirstChar  { it.uppercaseChar() },
                country = raceData.competition.location.country,
                idCompetition = raceData.competition.id,
                idRace = raceData.id,
                laps = raceData.laps.total.toString() + " laps"
            )
        }
    }
}
