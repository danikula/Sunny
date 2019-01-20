package com.danikula.sunny.data

import com.danikula.sunny.model.City
import com.danikula.sunny.web.ForecastApi
import io.reactivex.Observable

/**
 * Dummy implementation of [Repository].
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
class DummyRepository(api: ForecastApi, db: Database) : Repository(api, db) {

    private val cities = listOf(
        City(1, "Minsk", "BY"),
        City(2, "Vitebsk", "BY"),
        City(3, "Gomel", "BY"),
        City(4, "Grodno", "BY"),
        City(5, "Mogilev", "BY"),
        City(6, "Brest", "BY")
    )

    override fun searchCity(searchKey: String): Observable<List<City>> {
        return Observable.just(
            cities.filter { city -> !searchKey.isEmpty() && city.name.contains(searchKey, true) }
        )
    }
}