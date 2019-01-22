package com.danikula.sunny.androidtest.di

import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry
import com.danikula.sunny.data.*
import com.danikula.sunny.viewmodel.ViewModelFactory
import com.danikula.sunny.web.ForecastApi
import com.danikula.sunny.web.ForecastApiFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestAppModule {

    @Provides
    @Singleton
    fun provideContext(): Context = InstrumentationRegistry.getInstrumentation().context

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database =
        Room.inMemoryDatabaseBuilder(context, Database::class.java)
            .allowMainThreadQueries()
            .build()

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
    fun provideSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    fun provideSettings(sharedPreferences: SharedPreferences): Settings = Settings(sharedPreferences)

    @Provides
    fun provideSearchViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

}