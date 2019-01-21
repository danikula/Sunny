package com.danikula.sunny.web.dto

import com.danikula.sunny.model.Forecast

data class ForecastResponse(val list: List<CityForecast>)

data class CityForecast(val dt: Long, val main: Temperature) {
    fun toForecast(cityId: Long): Forecast {
        return Forecast(0, cityId, dt * 1000, main.toCelsius())
    }
}

data class Temperature(val temp: Float) {

    fun toCelsius(): Int {
        return Math.round(temp - 273.15).toInt()
    }
}
