package com.danikula.sunny.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.danikula.sunny.data.Repository
import com.danikula.sunny.data.Settings
import com.danikula.sunny.model.City
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CitiesListViewModel @Inject constructor(private val repository: Repository, private val settings: Settings) :
    BaseViewModel() {

    var cities: MutableLiveData<List<City>> = MutableLiveData()

    init {
        val disposable = repository.queryAllCities()
            .subscribeOn(Schedulers.io())
            .subscribe { cities.postValue(it) }
        addDisposable(disposable)
    }

    fun onCitySelected(city: City) {
        settings.activeCityId = city.id
    }

    fun deleteCity(city: City) {
        settings.activeCityId = null
        repository.deleteCity(city)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}
