package com.danikula.sunny.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.danikula.sunny.data.Repository
import com.danikula.sunny.data.Settings
import com.danikula.sunny.model.City
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CitiesListViewModel @Inject constructor(private val repository: Repository, private val settings: Settings) :
    ViewModel() {

    var cities: MutableLiveData<List<City>> = MutableLiveData()
    private val disposable: Disposable

    init {
        disposable = repository.queryAllCities()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { cities.postValue(it) }
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

    override fun onCleared() {
        disposable.dispose()
    }
}
