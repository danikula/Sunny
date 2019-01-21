package com.danikula.sunny.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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
    }

    private fun initUi() {
        val binding: ActivityForecastBinding = DataBindingUtil.setContentView(this, R.layout.activity_forecast)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        recyclerView.setup(forecastAdapter)
    }

    fun onSearchClick(view: View) {
        startActivity(Intent(this, SearchActivity::class.java))
    }

    fun onCitiesListClick(view: View) {
        startActivity(Intent(this, CitiesListActivity::class.java))
    }
}
