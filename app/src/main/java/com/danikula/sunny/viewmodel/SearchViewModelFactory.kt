package com.danikula.sunny.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(private val searchViewModel: SearchViewModel) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java!!)) {
            return searchViewModel as T
        }
        throw IllegalArgumentException("Invalid class: $modelClass")
    }
}