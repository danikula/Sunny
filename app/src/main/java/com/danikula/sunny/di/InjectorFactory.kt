package com.danikula.sunny.di

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
class InjectorFactory {

    companion object {
        fun newAppInjector(): AppComponent {
            return DaggerAppComponent.builder()
                .appModule(AppModule)
                .build()
        }
    }
}