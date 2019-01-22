package com.danikula.sunny.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.danikula.sunny.data.Repository
import com.danikula.sunny.data.Settings
import com.danikula.sunny.model.City
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: Repository, private val settings: Settings) :
    BaseViewModel() {

    val searchResult: MutableLiveData<List<City>> = MutableLiveData()
    val errors: MutableLiveData<Throwable> = MutableLiveData()

    fun search(searchKey: CharSequence) {
        val disposable = repository.searchCity(searchKey.toString())
            .subscribeOn(Schedulers.io())
            .subscribe({ searchResult.postValue(it) }, { errors.postValue(it) })
        addDisposable(disposable)
    }

    fun onCitySelected(city: City) {
        settings.activeCityId = city.id

        repository.insertCity(city)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}
