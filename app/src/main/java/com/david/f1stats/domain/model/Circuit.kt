package com.david.f1stats.domain.model

data class Circuit (
    val id: Int,
    val name: String,
    val country: String,
    val length: String,
    val laps: String,
    val firstGP: String,
    val lapRecordTime: String,
    val lapRecordDriver: String,
)