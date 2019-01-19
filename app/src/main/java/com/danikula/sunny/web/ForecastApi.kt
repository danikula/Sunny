package com.danikula.sunny.web

import com.danikula.sunny.web.dto.ForecastResponse
import com.danikula.sunny.web.dto.PlacesSearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An REST facade for [openweathermap api](https://openweathermap.org/forecast5)>.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
interface ForecastApi {

    @GET("data/2.5/find")
    fun findCity(@Query("q") searchKey: String): Observable<PlacesSearchResponse>

    @GET("data/2.5/forecast")
    fun getForecast(@Query("id") cityId: Int): Observable<ForecastResponse>

}