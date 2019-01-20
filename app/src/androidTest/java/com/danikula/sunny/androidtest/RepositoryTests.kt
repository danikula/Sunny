package com.danikula.sunny.androidtest

import android.support.test.runner.AndroidJUnit4
import com.danikula.sunny.androidtest.di.TestInjectorFactory
import com.danikula.sunny.data.Repository
import com.danikula.sunny.model.City
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Tests for persistent storage layer ([Repository])
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
@RunWith(AndroidJUnit4::class)
class RepositoryTests {

    @Inject
    lateinit var repository: Repository

    @Before
    fun setup() {
        TestInjectorFactory.newAppInjector().inject(this)
    }

    @Test
    fun testSavingSingleCity() {
        val city = City(1, "Minsk", "BY")
        repository.insertCity(city).test()

        val cities = repository.queryAllCities().test().values().first()
        assertThat(cities, hasSize(1))
        assertThat(cities.first(), equalTo(city))
    }

    @Test
    fun testDeletingCity() {
        val minsk = City(1, "Minsk", "BY")
        val gomel = City(2, "Gomel", "BY")
        repository.insertCity(minsk).test()
        repository.insertCity(gomel).test()

        repository.deleteCity(gomel).test()
        val cities = repository.queryAllCities().test().values().first()
        assertThat(cities, hasSize(1))
        assertThat(cities.first(), equalTo(minsk))
    }
}
