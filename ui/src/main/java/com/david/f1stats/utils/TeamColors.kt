package com.david.f1stats.utils

import com.david.f1stats.ui.R

val TEAM_COLOR_MAP = mapOf(
    1 to R.color.color_RedBull,
    2 to R.color.color_McLaren,
    3 to R.color.color_Ferrari,
    4 to R.color.color_ForceIndia,
    5 to R.color.color_Mercedes,
    6 to R.color.color_Lotus,
    7 to R.color.color_AlphaTauri,
    8 to R.color.color_Sauber,
    9 to R.color.color_Marussia,
    10 to R.color.color_Caterham,
    11 to R.color.color_HRT,
    12 to R.color.color_Williams,
    13 to R.color.color_Alpine,
    14 to R.color.color_Haas,
    15 to R.color.color_Virgin,
    16 to R.color.color_Manor,
    17 to R.color.color_AstonMartin,
    18 to R.color.color_AlfaRomeo
)

fun getColor(teamId: Int): Int {
    return TEAM_COLOR_MAP[teamId] ?: R.color.white
}
