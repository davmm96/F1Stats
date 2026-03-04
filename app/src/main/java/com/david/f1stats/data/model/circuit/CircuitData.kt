package com.david.f1stats.data.model.circuit

data class CircuitData(
    val capacity: Int?,
    val competition: CircuitCompetitionData?,
    val firstGrandPrix: Int?,
    val id: Int?,
    val image: String?,
    val lapRecord: CircuitLapRecordData?,
    val laps: Int?,
    val length: String?,
    val name: String?,
    val opened: Int?,
    val owner: Any?,
    val raceDistance: String?
)
