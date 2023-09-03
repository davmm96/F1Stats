@file:JvmName("AppUtils")
@file:JvmMultifileClass

package com.david.f1stats.utils

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun formatDate(dateTime: String, pattern: String): String {
    val dateTimeFormatted = OffsetDateTime.parse(dateTime)
    val format = DateTimeFormatter.ofPattern(pattern)

    return dateTimeFormatted.format(format)
}
