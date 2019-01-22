package com.danikula.sunny.androidtest.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.test.runner.AndroidJUnit4
import com.danikula.sunny.androidtest.di.TestInjectorFactory
import com.danikula.sunny.data.CityDao
import com.danikula.sunny.data.Settings
import com.danikula.sunny.model.City
import com.danikula.sunny.viewmodel.SearchViewModel
import com.danikula.sunny.viewmodel.ViewModelFactory
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
@RunWith(AndroidJUnit4::class)
class SearchViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var settings: Settings

    @Inject
    lateinit var cityDao: CityDao

    @Before
    fun setup() {
        TestInjectorFactory.newAppInjector().inject(this)
        searchViewModel = viewModelFactory.create(SearchViewModel::class.java)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun testSelectingCityFromSearchResults() {
        val minsk = City(5L, "Minsk", "BY")
        searchViewModel.onCitySelected(minsk)

        assertThat(settings.activeCityId, equalTo(5L))
        val cities = cityDao.queryAllCities().test().awaitCount(1).values().first()
        assertThat(cities, Matchers.hasSize(1))
        assertThat(cities.first(), equalTo(minsk))
    }

    @Test
    fun testSearching() {
        searchViewModel.search("London")

        assertThat(searchViewModel.searchResult.value, hasSize(5))
        assertThat(searchViewModel.errors.value, nullValue())
    }
}
