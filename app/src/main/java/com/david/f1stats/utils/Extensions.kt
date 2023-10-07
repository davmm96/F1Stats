@file:JvmName("AppUtils")
@file:JvmMultifileClass

package com.david.f1stats.utils

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun formatDate(dateTime: String, pattern: String): String {
    val dateTimeFormatted = OffsetDateTime.parse(dateTime).toInstant()
    val format = DateTimeFormatter.ofPattern(pattern).withZone(ZoneOffset.UTC)

    return format.format(dateTimeFormatted)
}

fun formatIntervalDate(dateTime: String, interval: Int): String {
    val dateTimeFormatted = OffsetDateTime.parse(dateTime)

    val startDay = dateTimeFormatted.minus(interval.toLong(), ChronoUnit.DAYS).dayOfMonth
    val raceDay = dateTimeFormatted.dayOfMonth

    return "$startDay-$raceDay"
}

fun dateToMillis(date: String): Long {
    val offsetDateTime = OffsetDateTime.parse(date)
    val instant = offsetDateTime.toInstant()
    return instant.toEpochMilli()
}
