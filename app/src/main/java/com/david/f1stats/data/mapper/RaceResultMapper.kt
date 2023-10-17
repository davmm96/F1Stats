package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.raceResult.RaceResultData
import com.david.f1stats.domain.model.RaceResult
import javax.inject.Inject

class RaceResultMapper @Inject constructor(): IMapper<List<RaceResultData>?, List<RaceResult>?> {

    override fun fromMap(from: List<RaceResultData>?): List<RaceResult>? {
        return from?.map { it.toRaceResult() }
    }

    private fun RaceResultData.toRaceResult() = RaceResult(
        position = position.toString(),
        driverAbbr = getAbbr(driver.abbr ?: "NF", driver.name),
        time = time ?: "0",
        points = getPoints(position).toString(),
        idTeam = team.id
    )

    private fun getPoints(position: Int): Int {
        return when (position) {
            1 -> 25
            2 -> 18
            3 -> 15
            4 -> 12
            5 -> 10
            6 -> 8
            7 -> 6
            8 -> 4
            9 -> 2
            10 -> 1
            else -> 0
        }
    }

    private fun getAbbr(abbr: String, name: String): String {
        return if(abbr != "NF")
            abbr
        else
            when (name) {
                "Oscar Piastri" -> "PIA"
                "Guanyu Zhou" -> "ZHO"
                "Liam Lawson" -> "LAW"
                "Logan Sargeant" -> "SAR"
                else -> "NF"
            }
    }
}