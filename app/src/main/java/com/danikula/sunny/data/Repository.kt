package com.danikula.sunny.data

import com.danikula.sunny.model.City
import com.danikula.sunny.web.ForecastApi
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * A web client with persistent cache.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
open class Repository @Inject constructor(
    private val api: ForecastApi, private val cityDao: CityDao
) {

    open fun searchCity(searchKey: String): Observable<List<City>> {
        return api.findCity(searchKey)
            .map { it.list.map { place -> place.toCity() } }
    }

    fun queryAllCities(): Flowable<List<City>> = cityDao.queryAllCities()

    fun queryCitiesCount(): Flowable<Int> = cityDao.queryCitiesCount()

    fun insertCity(city: City): Single<Long> {
        return Single.create { cityDao.insertCity(city) }
    }

    fun deleteCity(city: City): Single<Int> {
        return Single.create { cityDao.deleteCity(city) }
    }
}