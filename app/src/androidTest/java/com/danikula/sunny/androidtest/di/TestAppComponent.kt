package com.danikula.sunny.androidtest.di

import com.danikula.sunny.androidtest.DaoTests
import com.danikula.sunny.androidtest.RepositoryTests
import com.danikula.sunny.androidtest.SettingsTests
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent {

    fun inject(daoTest: DaoTests)

    fun inject(repositoryTest: RepositoryTests)

    fun inject(settingsTests: SettingsTests)
}