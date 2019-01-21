package com.danikula.sunny.di

import com.danikula.sunny.data.Repository
import com.danikula.sunny.ui.CitiesListActivity
import com.danikula.sunny.ui.ForecastActivity
import com.danikula.sunny.ui.SearchActivity
import com.danikula.sunny.viewmodel.SearchViewModel
import com.danikula.sunny.viewmodel.ViewModelFactory
import com.danikula.sunny.web.ForecastApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun provideForecastRestApi(): ForecastApi

    fun provideRepository(): Repository

    fun provideSearchViewModelFactory(): ViewModelFactory

    fun inject(searchViewModel: SearchViewModel)

    fun inject(searchActivity: SearchActivity)

    fun inject(citiesListActivity: CitiesListActivity)

    fun inject(forecastActivity: ForecastActivity)

}