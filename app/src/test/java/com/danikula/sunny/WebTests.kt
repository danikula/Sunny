package com.danikula.sunny

import com.danikula.sunny.di.TestInjectorFactory
import com.danikula.sunny.web.ForecastApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

/**
 * Tests for web layer
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
const val LONDON_ID: Int = 2643743

class WebTests {

    @Inject
    lateinit var api: ForecastApi

    @Before
    fun setup() {
        TestInjectorFactory
            .newAppInjector()
            .inject(this)
    }

    @Test
    fun testCitySearching() {
        val testObserver = api.findCity("London").test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
            .assertValue { places -> places.list.size == 5 }

        val searchResult = testObserver.values().first()
        searchResult.list.forEach { city -> assertEquals(city.name, "London") }
    }

    @Test
    fun testGettingValidForecast() {
        val testObserver = api.getForecast(LONDON_ID).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
            .assertValue { forecasts -> forecasts.list.isNotEmpty() }
    }
}
