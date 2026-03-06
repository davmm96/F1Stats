package com.david.f1stats.data.model.circuit

import com.google.gson.annotations.SerializedName

data class CircuitData(
    val capacity: Int?,
    val competition: CircuitCompetitionData?,
    @SerializedName("first_grand_prix")
    val firstGrandPrix: Int?,
    val id: Int?,
    val image: String?,
    @SerializedName("lap_record")
    val lapRecord: CircuitLapRecordData?,
    val laps: Int?,
    val length: String?,
    val name: String?,
    val opened: Int?,
    val owner: Any?,
    @SerializedName("race_distance")
    val raceDistance: String?
)
