package com.danikula.sunny.androidtest.di

import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import com.danikula.sunny.data.CityDao
import com.danikula.sunny.data.Database
import com.danikula.sunny.data.Repository
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
    fun provideForecastApi(): ForecastApi {
        return ForecastApiFactory.newApi()
    }

    @Provides
    @Singleton
    fun provideRepository(api: ForecastApi, cityDao: CityDao): Repository {
        return Repository(api, cityDao)
    }

    @Provides
    @Singleton
    fun provideCityDao(db: Database): CityDao {
        return db.cityDao()
    }
}