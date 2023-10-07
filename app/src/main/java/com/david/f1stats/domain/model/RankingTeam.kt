package com.david.f1stats.domain.model

data class RankingTeam (
    val idTeam: Int,
    val points: String,
    val position: Int,
    val name: String,
    val image: String,
)