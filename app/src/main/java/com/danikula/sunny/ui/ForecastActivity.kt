package com.danikula.sunny.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.danikula.sunny.R
import com.danikula.sunny.databinding.ActivityForecastBinding
import com.danikula.sunny.di.InjectorFactory
import com.danikula.sunny.ui.adapter.ForecastAdapter
import com.danikula.sunny.viewmodel.ForecastViewModel
import com.danikula.sunny.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*
import javax.inject.Inject

class ForecastActivity : AppCompatActivity() {

    private val LOG_TAG: String by lazy { ForecastActivity::class.java.toString() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val forecastAdapter = ForecastAdapter()
    private lateinit var viewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorFactory.newAppInjector(application).inject(this)
        initViewModel()
        initUi()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastViewModel::class.java)
        viewModel.forecast.observe(this, Observer {
            forecastAdapter.swapData(it ?: Arrays.asList())
        })
        viewModel.errors.observe(this, Observer { onError(it) })
    }

    private fun initUi() {
        val binding: ActivityForecastBinding = DataBindingUtil.setContentView(this, R.layout.activity_forecast)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        recyclerView.setup(forecastAdapter)
    }

    private fun onError(error: Throwable?) {
        Log.e(LOG_TAG, "Error fetching forecast", error)
        Toast.makeText(this, getString(R.string.forecast_error), Toast.LENGTH_LONG).show()
    }

    fun onSearchClick(view: View) {
        startActivity(Intent(this, SearchActivity::class.java))
    }

    fun onCitiesListClick(view: View) {
        startActivity(Intent(this, CitiesListActivity::class.java))
    }
}
