package com.danikula.sunny.model

import java.text.SimpleDateFormat
import java.util.*

data class Forecast(
    val cityId: Long,
    val timeInSeconds: Long,
    val temperature: Int
) {
    companion object {
        val DATE_FORMAT = SimpleDateFormat("dd MMMM HH:mm", Locale.US)
    }

    fun dateTime(): String {
        return DATE_FORMAT.format(Date(timeInSeconds * 1000))
    }
}
