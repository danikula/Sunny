package com.danikula.sunny

import com.danikula.sunny.web.ForecastApi
import com.danikula.sunny.web.ForecastApiFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests for web layer
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
const val LONDON_ID: Int = 2643743

class WebTests {

    private lateinit var api: ForecastApi

    @Before
    fun setup() {
        api = ForecastApiFactory.newApi()
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
