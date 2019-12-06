package ru.gorshkov.agrotask.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    fun getFieldDate(timeMills: Long) = getDate("dd.MM.yyyy", timeMills)

    fun getYear(timeMills: Long) = getDate("yyyy", timeMills)

    private fun getDate(format : String, timeMills: Long): String {
        return if(timeMills == 0L)
            ""
        else
            SimpleDateFormat(format, Locale.getDefault()).format(timeMills)
    }
}