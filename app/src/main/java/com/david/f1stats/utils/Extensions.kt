@file:JvmName("AppUtils")
@file:JvmMultifileClass

package com.david.f1stats.utils

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
