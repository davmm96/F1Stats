@file:JvmName("AppUtils")
@file:JvmMultifileClass

package com.david.f1stats.utils

import com.david.f1stats.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun formatDate(dateTime: String, pattern: String): String {
    val dateTimeParsed = OffsetDateTime.parse(dateTime)
    val formatter = DateTimeFormatter.ofPattern(pattern)

    return formatter.format(setOffset(dateTimeParsed))
}

fun formatIntervalDate(dateTime: String, interval: Int): String {
    val dateTimeParsed = OffsetDateTime.parse(dateTime)
    val dateTimeOffset = setOffset(dateTimeParsed)

    val startDay = dateTimeOffset.minus(interval.toLong(), ChronoUnit.DAYS).dayOfMonth
    val raceDay = dateTimeOffset.dayOfMonth

    return "$startDay-$raceDay"
}

fun dateToMillis(date: String): Long {
    val dateTimeParsed = OffsetDateTime.parse(date)
    val instant = setOffset(dateTimeParsed).toInstant()
    return instant.toEpochMilli()
}

private fun setOffset(dateTime: OffsetDateTime): OffsetDateTime{
    return dateTime.plusHours(dateTime.offset.totalSeconds.toLong() / 3600)
}

fun getColor(teamId: Int): Int{
    return when (teamId) {
        1 -> R.color.color_RedBull
        2 -> R.color.color_McLaren
        3 -> R.color.color_Ferrari
        4 -> R.color.color_ForceIndia
        5 -> R.color.color_Mercedes
        6 -> R.color.color_Lotus
        7 -> R.color.color_AlphaTauri
        8 -> R.color.color_Sauber
        9 -> R.color.color_Marussia
        10 -> R.color.color_Caterham
        11 -> R.color.color_HRT
        12 -> R.color.color_Williams
        13 -> R.color.color_Alpine
        14 -> R.color.color_Haas
        15 -> R.color.color_Virgin
        16 -> R.color.color_Manor
        17 -> R.color.color_AstonMartin
        18 -> R.color.color_AlfaRomeo
        else -> R.color.white
    }
}
