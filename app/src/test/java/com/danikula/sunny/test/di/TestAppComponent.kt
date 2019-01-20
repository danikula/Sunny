package com.danikula.sunny.test.di

import com.danikula.sunny.test.WebTests
import com.danikula.sunny.web.ForecastApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent {

    fun forecastRestApi(): ForecastApi

    fun inject(webTest: WebTests)
}