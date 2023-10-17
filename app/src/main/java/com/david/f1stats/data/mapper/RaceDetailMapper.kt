package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.StatusRaceEnum
import com.david.f1stats.domain.model.TypeRaceEnum
import com.david.f1stats.utils.Constants.FORMAT_DAY
import com.david.f1stats.utils.Constants.FORMAT_HOUR
import com.david.f1stats.utils.Constants.FORMAT_MONTH
import com.david.f1stats.utils.dateToMillis
import com.david.f1stats.utils.formatDate
import javax.inject.Inject

class RaceDetailMapper @Inject constructor(): IMapper<List<RaceData>?, List<RaceDetail>?> {

    override fun fromMap(from: List<RaceData>?): List<RaceDetail>? {
        return from?.mapNotNull { it.toRaceDetail() }
    }

    private fun RaceData.toRaceDetail(): RaceDetail? {
        val typeRace = getRaceType(type)
        val raceStatus = getRaceStatus(status)

        return if (raceStatus == StatusRaceEnum.SCHEDULED && typeRace != TypeRaceEnum.NONE) {
            RaceDetail(
                circuit = circuit.name,
                competition = competition.name,
                day = formatDate(date, FORMAT_DAY),
                month = formatDate(date, FORMAT_MONTH).replaceFirstChar { it.uppercaseChar() },
                hour = formatDate(date, FORMAT_HOUR),
                country = competition.location.country,
                id = competition.id,
                laps = "${laps.total} laps",
                type = typeRace,
                dateCalendar = dateToMillis(date),
                status = raceStatus,
            )
        } else {
            null
        }
    }

    private fun getRaceType(typeRace: String): TypeRaceEnum{
        val type: TypeRaceEnum = when (typeRace) {
            "Race" -> TypeRaceEnum.RACE
            "1st Qualifying" -> TypeRaceEnum.QUALY
            "1st Practice" -> TypeRaceEnum.P1
            "2nd Practice" -> TypeRaceEnum.P2
            "3rd Practice" -> TypeRaceEnum.P3
            "Sprint" -> TypeRaceEnum.SPRINT
            "1st Sprint Shootout" -> TypeRaceEnum.SPRINT_SHOOTOUT
            else -> {
                TypeRaceEnum.NONE
            }
        }
        return type
    }

    private fun getRaceStatus(raceStatus: String): StatusRaceEnum{
        val status: StatusRaceEnum = when (raceStatus) {
            "Live" -> StatusRaceEnum.LIVE
            "Finished" -> StatusRaceEnum.COMPLETED
            "Cancelled" -> StatusRaceEnum.CANCELLED
            "Postponed" -> StatusRaceEnum.POSTPONED
            "Scheduled" -> StatusRaceEnum.SCHEDULED
            else -> {
                StatusRaceEnum.NONE
            }
        }
        return status
    }
}
