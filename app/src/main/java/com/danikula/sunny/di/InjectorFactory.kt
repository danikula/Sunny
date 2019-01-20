package com.danikula.sunny.di

import android.app.Application

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
class InjectorFactory {

    companion object {
        fun newAppInjector(app: Application): AppComponent {
            return DaggerAppComponent.builder()
                .appModule(AppModule(app))
                .build()
        }
    }
}