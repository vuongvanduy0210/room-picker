package com.gianghv.android.util.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.dateFormatterDMY(): Date? {
    try {
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(this)
        return date
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

fun String.dateFormatterDMYHM(): Date? {
    try {
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(this)
        return date
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

fun Date.parseDateZ(): String {
    try {
        val format = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.US
        )

        format.timeZone = TimeZone.getTimeZone("UTC")
        val date = format.format(this)
        return date
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

fun Date.parseDateDMYHM(): String {
    try {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val date = format.format(this)
        return date
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}

fun String.dateFormatterZ(): Date? {
    try {
        val format = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.US
        )
        format.timeZone = TimeZone.getTimeZone("UTC")
        val date = format.parse(this)
        return date
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}
