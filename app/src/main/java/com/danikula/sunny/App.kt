package com.danikula.sunny

import android.app.Application
import android.util.Log
import io.reactivex.plugins.RxJavaPlugins

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
class App : Application() {

    companion object {
        private val LOG_TAG = this::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler {
            Log.e(LOG_TAG, "Unhandled rxjava error", it)
        }
    }
}