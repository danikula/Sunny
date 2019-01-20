package com.danikula.sunny.androidtest.di

import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import com.danikula.sunny.data.Database
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
}