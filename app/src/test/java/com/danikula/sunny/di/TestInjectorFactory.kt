package com.danikula.sunny.di

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
class TestInjectorFactory {

    companion object {
        fun newAppInjector(): TestAppComponent {
            return DaggerTestAppComponent.builder()
                .appModule(AppModule)
                .build()
        }
    }
}