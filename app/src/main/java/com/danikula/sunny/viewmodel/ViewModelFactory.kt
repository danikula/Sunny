package com.danikula.sunny.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.danikula.sunny.data.Repository
import com.danikula.sunny.data.Settings
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val repository: Repository,
    private val settings: Settings
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(repository, settings) as T
            modelClass.isAssignableFrom(CitiesListViewModel::class.java) -> CitiesListViewModel(repository, settings) as T
            modelClass.isAssignableFrom(ForecastViewModel::class.java) -> ForecastViewModel(repository, settings) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}