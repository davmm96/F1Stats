package com.david.f1stats.ui.races.raceDetail

import android.content.Context
import com.david.f1stats.domain.model.TypeRaceEnum
import com.david.f1stats.ui.R

fun TypeRaceEnum.getLabel(context: Context): String = when (this) {
    TypeRaceEnum.RACE -> context.getString(R.string.race)
    TypeRaceEnum.QUALY -> context.getString(R.string.qualy)
    TypeRaceEnum.P3 -> context.getString(R.string.p3)
    TypeRaceEnum.P2 -> context.getString(R.string.p2)
    TypeRaceEnum.P1 -> context.getString(R.string.p1)
    TypeRaceEnum.SPRINT -> context.getString(R.string.sprint)
    TypeRaceEnum.SPRINT_SHOOTOUT -> context.getString(R.string.sprint_shootout)
    TypeRaceEnum.NONE -> "NONE"
}
