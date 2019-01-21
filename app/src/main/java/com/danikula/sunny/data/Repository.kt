package com.danikula.sunny.data

import com.danikula.sunny.model.City
import com.danikula.sunny.model.Forecast
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
    private val api: ForecastApi,
    private val cityDao: CityDao,
    private val forecastDao: ForecastDao
) {

    open fun searchCity(searchKey: String): Observable<List<City>> {
        return api.findCity(searchKey)
            .map { it.list.map { place -> place.toCity() } }
    }

    fun queryAllCities(): Flowable<List<City>> = cityDao.queryAllCities()

    fun queryCitiesCount(): Flowable<Int> = cityDao.queryCitiesCount()

    fun queryCity(cityId: Long): Flowable<City> = cityDao.queryCity(cityId)

    fun insertCity(city: City): Single<Long> {
        return Single.create { cityDao.insertCity(city) }
    }

    fun deleteCity(city: City): Single<Int> {
        return Single.create { cityDao.deleteCity(city) }
    }

    fun queryForecast(cityId: Long): Observable<List<Forecast>> {
        val apiForecast = fetchForecastFromApi(cityId)
        val dbForecast = forecastDao.queryForCity(cityId).toObservable()
        return Observable.mergeDelayError(apiForecast, dbForecast)
    }

    private fun fetchForecastFromApi(cityId: Long): Observable<List<Forecast>> {
        return api.getForecast(cityId)
            .map { it.list.map { forecastDto -> forecastDto.toForecast(cityId) } }
            .doOnNext { forecastDao.updateForCity(cityId, it) }
    }
}