package com.danikula.sunny.data

import com.danikula.sunny.model.City
import com.danikula.sunny.web.ForecastApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * A web client with persistent cache.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
open class Repository @Inject constructor(private val api: ForecastApi, private val db: Database) {

    open fun searchCity(searchKey: String): Observable<List<City>> {
        return api.findCity(searchKey)
            .map { it.list.map { place -> place.toCity() } }
    }
}