package com.danikula.sunny.web

import com.danikula.sunny.OPEN_WEATHER_MAP_APP_ID
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


/**
 * An factory for [com.danikula.sunny.web.ForecastApi].
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
class ForecastApiFactory {

    companion object {
        fun newApi(): ForecastApi {
            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(newHttpClient())
                .build()
                .create(ForecastApi::class.java)
        }

        private fun newHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(Authorizer())
                .build()
        }
    }

    private class Authorizer : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val newUrl = request.url()
                .newBuilder()
                .addQueryParameter("appid", OPEN_WEATHER_MAP_APP_ID)
                .build()
            val modifiedRequest = request.newBuilder()
                .url(newUrl)
                .build()
            return chain.proceed(modifiedRequest)
        }
    }
}