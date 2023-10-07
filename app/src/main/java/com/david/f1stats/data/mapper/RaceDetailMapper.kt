package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.TypeRace
import com.david.f1stats.utils.Constants.FORMAT_DAY
import com.david.f1stats.utils.Constants.FORMAT_HOUR
import com.david.f1stats.utils.Constants.FORMAT_MONTH
import com.david.f1stats.utils.dateToMillis
import com.david.f1stats.utils.formatDate
import javax.inject.Inject

class RaceDetailMapper @Inject constructor(): IMapper<List<RaceData>?, List<RaceDetail>?> {
    override fun fromMap(from: List<RaceData>?): List<RaceDetail>? {
        return from?.map { raceData ->
            RaceDetail(
                circuit = raceData.circuit.name,
                competition = raceData.competition.name,
                day = formatDate(raceData.date,FORMAT_DAY),
                month = formatDate(raceData.date,FORMAT_MONTH).replaceFirstChar  { it.uppercaseChar() },
                hour = formatDate(raceData.date, FORMAT_HOUR),
                country = raceData.competition.location.country,
                id = raceData.competition.id,
                laps = raceData.laps.total.toString() + " laps",
                type = getRaceType(raceData.type),
                dateCalendar = dateToMillis(raceData.date)
            )
        }
    }

    private fun getRaceType(typeRace: String): TypeRace{
        val type: TypeRace = when (typeRace) {
            "Race" -> TypeRace.RACE
            "1st Qualifying" -> TypeRace.QUALY
            "1st Practice" -> TypeRace.P1
            "2nd Practice" -> TypeRace.P2
            "3rd Practice" -> TypeRace.P3
            "Sprint" -> TypeRace.SPRINT
            "1st Sprint Shootout" -> TypeRace.SPRINT_SHOOTOUT
            else -> {
                TypeRace.NONE
            }
        }
        return type
    }
}
