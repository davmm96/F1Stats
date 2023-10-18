package com.david.f1stats.data.mapper

import com.david.f1stats.R
import com.david.f1stats.data.model.raceResult.RaceResultData
import com.david.f1stats.domain.model.RaceResult
import com.david.f1stats.utils.Constants
import javax.inject.Inject

class RaceResultMapper @Inject constructor(): IMapper<List<RaceResultData>?, List<RaceResult>?> {

    companion object {
        val POINTS_MAP = mapOf(
            1 to 25,
            2 to 18,
            3 to 15,
            4 to 12,
            5 to 10,
            6 to 8,
            7 to 6,
            8 to 4,
            9 to 2,
            10 to 1
        )

        private const val NOT_FOUND_ABBR = "NF"
        private const val PIASTRI_ABBR = "PIA"
        private const val ZHOU_ABBR = "ZHO"
        private const val LAWSON_ABBR = "LAW"
        private const val SARGEANT_ABBR = "SAR"
    }


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
        return POINTS_MAP[position] ?: 0
    }

    private fun getAbbr(abbr: String, name: String): String {
        return if(abbr != NOT_FOUND_ABBR)
            abbr
        else
            when (name) {
                "Oscar Piastri" -> PIASTRI_ABBR
                "Guanyu Zhou" -> ZHOU_ABBR
                "Liam Lawson" -> LAWSON_ABBR
                "Logan Sargeant" -> SARGEANT_ABBR
                else -> NOT_FOUND_ABBR
            }
    }
}
