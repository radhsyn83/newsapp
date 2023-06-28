package com.radhsyn83.newsapp.common

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object Tools {
    @SuppressLint("SimpleDateFormat")
    fun dateFormat(time: String): String {
        var dateTime: Date? = null
        var dateTime2: Date? = null
        try {
            dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val calendar = Calendar.getInstance()
        calendar.time = dateTime!!

        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DATE, -1)

        try {
            dateTime2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val calendar2 = Calendar.getInstance()
        calendar2.time = dateTime2

        return if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(
                Calendar.DAY_OF_YEAR
            )
        ) {
            SimpleDateFormat("HH:mm").format(dateTime)
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(
                Calendar.DAY_OF_YEAR
            ) == yesterday.get(
                Calendar.DAY_OF_YEAR
            )
        ) {
            "Yesterday, " + SimpleDateFormat("HH:mm").format(dateTime)
        } else {
            SimpleDateFormat("dd/MM/yyyy").format(dateTime) + ", " + SimpleDateFormat("HH:mm").format(
                dateTime
            )
        }
    }
}