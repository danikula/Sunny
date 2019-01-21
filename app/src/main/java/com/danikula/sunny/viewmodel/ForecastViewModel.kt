package com.danikula.sunny.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import com.danikula.sunny.data.Repository
import com.danikula.sunny.data.Settings
import com.danikula.sunny.model.City
import com.danikula.sunny.model.Forecast
import io.reactivex.schedulers.Schedulers

class ForecastViewModel(repository: Repository, settings: Settings) : BaseViewModel() {

    val hasSavedCities: ObservableBoolean = ObservableBoolean(false)
    val city: MutableLiveData<City> = MutableLiveData()
    val forecast: MutableLiveData<List<Forecast>> = MutableLiveData()
    val errors: MutableLiveData<Throwable> = MutableLiveData()

    init {
        var disposable = repository.queryCitiesCount()
            .subscribeOn(Schedulers.io())
            .subscribe { hasSavedCities.set(it > 0) }
        addDisposable(disposable)

        val activeCityId = settings.activeCityId
        activeCityId?.let {
            disposable = repository.queryCity(activeCityId)
                .subscribeOn(Schedulers.io())
                .subscribe { c -> city.postValue(c) }
            addDisposable(disposable)

            disposable = repository.queryForecast(activeCityId)
                .subscribeOn(Schedulers.io())
                .subscribe({ f -> forecast.postValue(f) }, { t -> errors.postValue(t) })
            addDisposable(disposable)
        }
    }
}
