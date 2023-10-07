package com.david.f1stats.utils

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import javax.inject.Inject

class CalendarHelper @Inject constructor()  {
    fun addToCalendar(context: Context, title: String, location: String, startMillis: Long) {
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.Events.TITLE, title)
            .putExtra(CalendarContract.Events.EVENT_TIMEZONE, Constants.TIMEZONE)
            .putExtra(CalendarContract.Events.DESCRIPTION, "F1 Race Event")
            .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
        context.startActivity(intent)
    }
}