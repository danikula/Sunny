package com.danikula.sunny.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.widget.LinearLayout
import com.danikula.sunny.R
import com.danikula.sunny.di.InjectorFactory
import com.danikula.sunny.viewmodel.SearchViewModel
import com.danikula.sunny.viewmodel.SearchViewModelFactory
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.search_activity.*
import java.util.Arrays.asList
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    private val LOG_TAG: String by lazy { SearchActivity::class.java.toString() }

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel
    private val searchResultsAdapter = CityAdapter()
    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorFactory.newAppInjector(application).inject(this)
        initViewModel()
        initUi()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, searchViewModelFactory).get(SearchViewModel::class.java)
        viewModel.searchResult.observe(this, Observer {
            searchResultsAdapter.swapData(it ?: asList())
        })
        viewModel.errors.observe(this, Observer { onError(it) })
    }

    private fun initUi() {
        setContentView(R.layout.search_activity)
        initRecyclerView()

        disposable = TextChangeObserver()
            .observeTextChanges(searchEditText)
            .subscribe { viewModel.search(it) }
    }

    private fun initRecyclerView() {
        val divider = DividerItemDecoration(this, LinearLayout.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.list_divider)!!)

        recyclerView.apply {
            setHasFixedSize(true)
            adapter = searchResultsAdapter
            addItemDecoration(divider)
        }
    }

    private fun onError(error: Throwable?) {
        Log.e(LOG_TAG, "Error searching a city", error)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
