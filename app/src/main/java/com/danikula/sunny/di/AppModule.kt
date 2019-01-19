package com.danikula.sunny.di

import android.arch.lifecycle.ViewModelProvider
import com.danikula.sunny.data.Repository
import com.danikula.sunny.viewmodel.SearchViewModelFactory
import com.danikula.sunny.web.ForecastApi
import com.danikula.sunny.web.ForecastApiFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object AppModule {

    @Provides
    @Singleton
    fun provideForecastApi(): ForecastApi {
        return ForecastApiFactory.newApi()
    }

    @Provides
    @Singleton
    fun provideRepository(api: ForecastApi): Repository {
        return Repository(api)
    }

    @Provides
    fun provideSearchViewModelFactory(factory: SearchViewModelFactory): ViewModelProvider.Factory = factory
}