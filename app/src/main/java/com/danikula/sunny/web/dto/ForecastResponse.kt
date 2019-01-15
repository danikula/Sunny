package com.danikula.sunny.web.dto

import com.danikula.sunny.model.Forecast

data class ForecastResponse(val list: List<CityForecast>)

data class CityForecast(val dt: Long, val main: Temperature) {
    fun toForecast(cityId: Int): Forecast {
        return Forecast(cityId, dt, main.temp)
    }
}

data class Temperature(val temp: Float)
