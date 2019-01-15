package com.danikula.sunny.model

data class Forecast(
    val cityId: Int,
    val timeInSeconds: Long,
    val temperature: Float
)
