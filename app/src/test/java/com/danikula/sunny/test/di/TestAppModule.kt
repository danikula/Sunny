package com.danikula.sunny.test.di

import com.danikula.sunny.web.ForecastApi
import com.danikula.sunny.web.ForecastApiFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestAppModule {

    @Provides
    @Singleton
    fun provideForecastApi(): ForecastApi {
        return ForecastApiFactory.newApi()
    }
}