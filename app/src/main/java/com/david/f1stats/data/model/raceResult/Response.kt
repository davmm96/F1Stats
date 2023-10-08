package com.david.f1stats.data.model.raceResult

data class Response(
    val driver: Driver,
    val gap: String,
    val grid: String,
    val laps: Int,
    val pits: Any,
    val position: Int,
    val race: Race,
    val team: Team,
    val time: String
)