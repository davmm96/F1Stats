package com.david.f1stats.domain.model

import android.content.Context
import com.david.f1stats.R

enum class TypeRace(private val stringRes: Int) {
    RACE(R.string.race),
    QUALY(R.string.qualy),
    P3(R.string.p3),
    P2(R.string.p2),
    P1(R.string.p1),
    SPRINT(R.string.sprint),
    SPRINT_SHOOTOUT(R.string.sprint_shootout),
    NONE(0);  // No string resource for NONE

    fun getString(context: Context): String {
        return if (stringRes != 0) context.getString(stringRes) else "NONE"
    }
}
