package com.danikula.sunny.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.danikula.sunny.R
import com.danikula.sunny.di.InjectorFactory
import com.danikula.sunny.model.City
import com.danikula.sunny.viewmodel.SearchViewModel
import com.danikula.sunny.viewmodel.ViewModelFactory
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_search.*
import java.util.Arrays.asList
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), CityAdapter.OnCityClickListener {

    private val LOG_TAG: String by lazy { SearchActivity::class.java.toString() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SearchViewModel
    private val searchResultsAdapter = CityAdapter(this)
    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorFactory.newAppInjector(application).inject(this)
        initViewModel()
        initUi()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        viewModel.searchResult.observe(this, Observer {
            searchResultsAdapter.swapData(it ?: asList())
        })
        viewModel.errors.observe(this, Observer { onError(it) })
    }

    private fun initUi() {
        setContentView(R.layout.activity_search)
        recyclerView.setup(searchResultsAdapter)

        disposable = TextChangeObserver()
            .observeTextChanges(searchEditText)
            .subscribe { viewModel.search(it) }
    }

    private fun onError(error: Throwable?) {
        Log.e(LOG_TAG, "Error searching a city", error)
    }

    override fun onCityClick(city: City) {
        viewModel.onCitySelected(city)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
