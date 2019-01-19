package com.danikula.sunny.di

import com.danikula.sunny.data.Repository
import com.danikula.sunny.ui.SearchActivity
import com.danikula.sunny.viewmodel.SearchViewModel
import com.danikula.sunny.viewmodel.SearchViewModelFactory
import com.danikula.sunny.web.ForecastApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun provideForecastRestApi(): ForecastApi

    fun provideRepository(): Repository

    fun provideSearchViewModelFactory(): SearchViewModelFactory

    fun inject(searchViewModel: SearchViewModel)

    fun inject(searchActivity: SearchActivity)

}