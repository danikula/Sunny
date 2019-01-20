package com.danikula.sunny.test.di

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
class TestInjectorFactory {

    companion object {
        fun newAppInjector(): TestAppComponent {
            return DaggerTestAppComponent.builder()
                .testAppModule(TestAppModule())
                .build()
        }
    }
}