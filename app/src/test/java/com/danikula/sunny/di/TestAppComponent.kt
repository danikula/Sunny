package com.danikula.sunny.di

import com.danikula.sunny.WebTests
import com.danikula.sunny.web.ForecastApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface TestAppComponent {

    fun forecastRestApi(): ForecastApi

    fun inject(webTest: WebTests)
}