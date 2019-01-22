package com.danikula.sunny.androidtest

import android.support.test.runner.AndroidJUnit4
import com.danikula.sunny.androidtest.di.TestInjectorFactory
import com.danikula.sunny.data.CityDao
import com.danikula.sunny.data.Database
import com.danikula.sunny.data.ForecastDao
import com.danikula.sunny.model.City
import com.danikula.sunny.model.Forecast
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Tests for persistent storage layer ([CityDao], [ForecastDao])
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
@RunWith(AndroidJUnit4::class)
class DaoTests {

    @Inject
    lateinit var db: Database

    @Inject
    lateinit var cityDao: CityDao

    @Inject
    lateinit var forecastDao: ForecastDao

    @Before
    fun setup() {
        TestInjectorFactory.newAppInjector().inject(this)
    }

    @After
    fun release() {
        db.close()
    }

    @Test
    fun testSavingSingleCity() {
        val city = City(1, "Minsk", "BY")
        cityDao.insertCity(city)

        val cities = cityDao.queryAllCities().test().values().first()
        assertThat(cities, hasSize(1))
        assertThat(cities.first(), equalTo(city))
    }

    @Test
    fun testDeletingCity() {
        val minsk = City(1, "Minsk", "BY")
        val gomel = City(2, "Gomel", "BY")
        cityDao.insertCity(minsk)
        cityDao.insertCity(gomel)

        cityDao.deleteCity(gomel)
        val cities = cityDao.queryAllCities().test().awaitCount(1).values().first()
        assertThat(cities, hasSize(1))
        assertThat(cities.first(), equalTo(minsk))
    }

    @Test
    fun testCitiesCount() {
        var count = cityDao.queryCitiesCount().test().awaitCount(1).values().first()
        assertThat(count, equalTo(0))

        cityDao.insertCity(City(1, "Minsk", "BY"))

        count = cityDao.queryCitiesCount().test().awaitCount(1).values().first()
        assertThat(count, equalTo(1))
    }

    @Test
    fun testInsertAndQuery() {
        val minskId = 1L
        val brestId = 2L
        cityDao.insertCity(City(minskId, "Minsk", "BY"))
        cityDao.insertCity(City(brestId, "Brest", "BY"))

        forecastDao.insertMany(
            listOf(
                Forecast(0, minskId, 0, 0),
                Forecast(0, brestId, 0, 0)
            )
        )

        val minskForecast = forecastDao.queryForCity(minskId).test().awaitCount(1).values().first()
        val brestForecast = forecastDao.queryForCity(brestId).test().awaitCount(1).values().first()

        assertThat(minskForecast, hasSize(1))
        assertThat(brestForecast, hasSize(1))
    }

    @Test
    fun testCascadeDelete() {
        val minskId = 1L
        val city = City(minskId, "Minsk", "BY")
        cityDao.insertCity(city)
        forecastDao.insertMany(
            listOf(
                Forecast(0, minskId, 0, 0),
                Forecast(0, minskId, 0, 0)
            )
        )
        cityDao.deleteCity(city)

        val minskForecast = forecastDao.queryForCity(minskId).test().awaitCount(1).values().first()
        assertThat(minskForecast, empty())
    }

    @Test
    fun testUpdateForecastsForCity() {
        val minskId = 1L
        cityDao.insertCity(City(minskId, "Minsk", "BY"))
        cityDao.insertCity(City(2, "Gomel", "BY"))
        forecastDao.insertMany(
            listOf(
                Forecast(0, minskId, 0, 0),
                Forecast(0, 2, 0, 0)
            )
        )

        forecastDao.updateForCity(
            minskId, listOf(
                Forecast(0, minskId, 0, 1)
            )
        )

        val minskForecast = forecastDao.queryForCity(minskId).test().awaitCount(1).values().first()
        assertThat(minskForecast, hasSize(1))
        assertThat(minskForecast.first().temperature, equalTo(1))
    }
}
