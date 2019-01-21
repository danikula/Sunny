package com.danikula.sunny.androidtest

import android.content.SharedPreferences
import android.support.test.runner.AndroidJUnit4
import com.danikula.sunny.androidtest.di.TestInjectorFactory
import com.danikula.sunny.data.Settings
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Tests for [Settings]
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
@RunWith(AndroidJUnit4::class)
class SettingsTests {

    @Inject
    lateinit var settings: Settings

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setup() {
        TestInjectorFactory.newAppInjector().inject(this)
        sharedPreferences.edit().clear().commit()
    }

    @Test
    fun testGettingDefaultActiveCityId() {
        assertThat(settings.activeCityId, nullValue())
    }

    @Test
    fun testSavingActiveCityId() {
        val cityId = 42L
        settings.activeCityId = cityId
        assertThat(settings.activeCityId, equalTo(cityId))
    }
}
