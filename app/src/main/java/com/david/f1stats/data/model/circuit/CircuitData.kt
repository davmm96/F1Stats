package com.david.f1stats.data.model.circuit

data class CircuitData(
    val capacity: Int?,
    val competition: CircuitCompetitionData?,
    val first_grand_prix: Int?,
    val id: Int?,
    val image: String?,
    val lap_record: CircuitLapRecordData?,
    val laps: Int?,
    val length: String?,
    val name: String?,
    val opened: Int?,
    val owner: Any?,
    val race_distance: String?
)