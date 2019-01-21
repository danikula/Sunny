package com.danikula.sunny.di

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.preference.PreferenceManager
import com.danikula.sunny.data.*
import com.danikula.sunny.viewmodel.ViewModelFactory
import com.danikula.sunny.web.ForecastApi
import com.danikula.sunny.web.ForecastApiFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideForecastApi(): ForecastApi = ForecastApiFactory.newApi()

    @Provides
    @Singleton
    fun provideRepository(api: ForecastApi, cityDao: CityDao, forecastDao: ForecastDao): Repository =
        Repository(api, cityDao, forecastDao)

    @Provides
    @Singleton
    fun provideCityDao(db: Database): CityDao = db.cityDao()

    @Provides
    @Singleton
    fun provideForecastDao(db: Database): ForecastDao = db.forecastDao()

    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database =
        Room.databaseBuilder(app, Database::class.java, "sunny_db").build()

    @Provides
    fun provideSearchViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun provideSettings(app: Application): Settings = Settings(PreferenceManager.getDefaultSharedPreferences(app))
}