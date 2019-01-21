package com.danikula.sunny.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.danikula.sunny.data.Repository
import com.danikula.sunny.data.Settings
import com.danikula.sunny.model.City
import com.danikula.sunny.model.Forecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ForecastViewModel(repository: Repository, settings: Settings) : ViewModel() {

    val hasSavedCities: ObservableBoolean = ObservableBoolean(false)
    var city: MutableLiveData<City> = MutableLiveData()
    var forecast: MutableLiveData<List<Forecast>> = MutableLiveData()
    var errors: MutableLiveData<Throwable> = MutableLiveData()
    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        var disposable = repository.queryCitiesCount()
            .subscribe { hasSavedCities.set(it > 0) }
        disposables.add(disposable)

        val activeCityId = settings.activeCityId
        activeCityId?.let {
            disposable = repository.queryCity(activeCityId)
                .subscribe { c -> city.postValue(c) }
            disposables.add(disposable)

            disposable = repository.queryForecast(activeCityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ f -> forecast.postValue(f) }, { t -> errors.postValue(t) })
            disposables.add(disposable)
        }
    }

    override fun onCleared() {
        disposables.dispose()
    }
}
