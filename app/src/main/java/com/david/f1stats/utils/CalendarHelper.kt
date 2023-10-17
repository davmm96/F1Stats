package com.david.f1stats.utils

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import com.david.f1stats.utils.Constants.TIMEZONE
import javax.inject.Inject

class CalendarHelper @Inject constructor()  {

    data class CalendarEvent(
        val title: String,
        val description: String,
        val location: String,
        val startMillis: Long
    )

    fun addToCalendar(context: Context, event: CalendarEvent) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.startMillis)
            putExtra(CalendarContract.Events.TITLE, event.title)
            putExtra(CalendarContract.Events.EVENT_TIMEZONE, TIMEZONE)
            putExtra(CalendarContract.Events.DESCRIPTION, event.description)
            putExtra(CalendarContract.Events.EVENT_LOCATION, event.location)
        }
        context.startActivity(intent)
    }
}
