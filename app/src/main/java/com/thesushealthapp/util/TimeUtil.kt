package com.thesushealthapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class TimeUtil {

    companion object {

        //TODO check if the timezone is ok
        fun getDateFormattedNow() : String {
            val sdf =
                SimpleDateFormat("d 'of' MMMM 'of' yyyy 'at' HH:mm:ss 'UTC-3'", Locale("en", "USA"))
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"))
            val date = Date(System.currentTimeMillis())
            return sdf.format(date)
        }
    }
}