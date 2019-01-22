package com.danikula.sunny.androidtest.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.SharedPreferences
import android.support.test.runner.AndroidJUnit4
import com.danikula.sunny.androidtest.di.TestInjectorFactory
import com.danikula.sunny.data.Repository
import com.danikula.sunny.data.Settings
import com.danikula.sunny.model.City
import com.danikula.sunny.viewmodel.CitiesListViewModel
import com.danikula.sunny.viewmodel.ViewModelFactory
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
@RunWith(AndroidJUnit4::class)
class CitiesListViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var citiesListViewModel: CitiesListViewModel

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var settings: Settings

    @Inject
    lateinit var repository: Repository

    @Before
    fun setup() {
        TestInjectorFactory.newAppInjector().inject(this)
        citiesListViewModel = viewModelFactory.create(CitiesListViewModel::class.java)

        sharedPreferences.edit().clear().commit()

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun testSelectingCity() {
        assertThat(settings.activeCityId, nullValue())

        val minsk = City(5L, "Minsk", "BY")
        citiesListViewModel.onCitySelected(minsk)

        assertThat(settings.activeCityId, equalTo(5L))
    }

    @Test
    fun testDisplayingCitiesList() {
        assertThat(citiesListViewModel.cities.value, nullValue())

        repository.insertCity(City(1, "Minsk", "BY")).test()

        assertThat(citiesListViewModel.cities.value, hasSize(1))
    }
}
