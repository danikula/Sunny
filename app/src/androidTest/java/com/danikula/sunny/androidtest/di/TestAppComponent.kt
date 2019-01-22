package com.danikula.sunny.androidtest.di

import com.danikula.sunny.androidtest.DaoTests
import com.danikula.sunny.androidtest.RepositoryTests
import com.danikula.sunny.androidtest.SettingsTests
import com.danikula.sunny.androidtest.viewmodel.CitiesListViewModelTest
import com.danikula.sunny.androidtest.viewmodel.SearchViewModelTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent {

    fun inject(daoTest: DaoTests)

    fun inject(repositoryTest: RepositoryTests)

    fun inject(settingsTests: SettingsTests)

    fun inject(searchViewModelTest: SearchViewModelTest)

    fun inject(citiesListViewModelTest: CitiesListViewModelTest)
}