@file:JvmName("AppUtils")
@file:JvmMultifileClass

package com.david.f1stats.utils

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun formatDate(dateTime: String): String {

    val dateTimeFormatted = OffsetDateTime.parse(dateTime)
    val format2 = DateTimeFormatter.ofPattern("dd LL yy HH:mm")

    return dateTimeFormatted.format(format2)
}
