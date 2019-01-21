package com.danikula.sunny.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.danikula.sunny.data.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ForecastViewModel(repository: Repository) : ViewModel() {

    val hasSavedCities: ObservableBoolean = ObservableBoolean(false)
    private val disposable: Disposable = repository.queryCitiesCount()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { hasSavedCities.set(it > 0) }

    override fun onCleared() {
        disposable.dispose()
    }
}
