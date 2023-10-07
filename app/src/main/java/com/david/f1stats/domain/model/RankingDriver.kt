package com.david.f1stats.domain.model

data class RankingDriver (
    val position: Int,
    val idDriver: Int,
    val image: String,
    val name: String,
    val team: String,
    val points: String,
)