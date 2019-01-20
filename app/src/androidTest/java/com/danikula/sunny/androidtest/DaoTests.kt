package com.danikula.sunny.androidtest

import android.support.test.runner.AndroidJUnit4
import com.danikula.sunny.androidtest.di.TestInjectorFactory
import com.danikula.sunny.data.CityDao
import com.danikula.sunny.data.Database
import com.danikula.sunny.model.City
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
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
        val cities = cityDao.queryAllCities().test().values().first()
        assertThat(cities, hasSize(1))
        assertThat(cities.first(), equalTo(minsk))
    }
}
