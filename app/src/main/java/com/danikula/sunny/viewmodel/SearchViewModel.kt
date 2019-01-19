package com.danikula.sunny.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.danikula.sunny.data.Repository
import com.danikula.sunny.model.City
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var searchResult: MutableLiveData<List<City>> = MutableLiveData()
    var errors: MutableLiveData<Throwable> = MutableLiveData()
    private val disposables: CompositeDisposable = CompositeDisposable()

    fun search(searchKey: CharSequence) {
        val disposable = repository.searchCity(searchKey.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchResult.postValue(it) }, { errors.postValue(it) })
        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.dispose()
    }
}
