package com.danikula.sunny.di

import com.danikula.sunny.web.ForecastApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun forecastRestApi(): ForecastApi

}