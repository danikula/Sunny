package com.danikula.sunny.androidtest.di

import com.danikula.sunny.androidtest.StorageTests
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent {

    fun inject(storageTest: StorageTests)
}