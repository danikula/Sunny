package com.danikula.sunny.di

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import com.danikula.sunny.data.CityDao
import com.danikula.sunny.data.Database
import com.danikula.sunny.data.Repository
import com.danikula.sunny.viewmodel.SearchViewModelFactory
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

    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database =
        Room.databaseBuilder(app, Database::class.java, "sunny_db").build()

    @Provides
    fun provideSearchViewModelFactory(factory: SearchViewModelFactory): ViewModelProvider.Factory = factory
}