package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.domain.model.Race
import com.david.f1stats.utils.Constants.FORMAT_MONTH
import com.david.f1stats.utils.formatDate
import com.david.f1stats.utils.formatIntervalDate
import javax.inject.Inject

class RaceMapper @Inject constructor(): IMapper<List<RaceData>?, List<Race>?> {

    override fun fromMap(from: List<RaceData>?): List<Race>? {
        return from?.map { it.toRace() }
    }

    private fun RaceData.toRace() = Race(
        competition = competition.name,
        dayInterval = formatIntervalDate(date, 2),
        month = formatDate(date, FORMAT_MONTH).replaceFirstChar { it.uppercaseChar() },
        country = competition.location.country,
        idCompetition = competition.id,
        idRace = id,
        laps = "${laps.total} laps",
        season = season.toString()
    )
}
