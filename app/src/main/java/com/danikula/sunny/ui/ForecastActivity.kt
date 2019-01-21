package com.danikula.sunny.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.danikula.sunny.R
import com.danikula.sunny.databinding.ActivityForecastBinding
import com.danikula.sunny.di.InjectorFactory
import com.danikula.sunny.viewmodel.ForecastViewModel
import com.danikula.sunny.viewmodel.ViewModelFactory
import javax.inject.Inject

class ForecastActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorFactory.newAppInjector(application).inject(this)
        initViewModel()
        initUi()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastViewModel::class.java)
    }

    private fun initUi() {
        val binding: ActivityForecastBinding = DataBindingUtil.setContentView(this, R.layout.activity_forecast)
        binding.viewModel = viewModel
    }

    fun onSearchClick(view: View) {
        startActivity(Intent(this, SearchActivity::class.java))
    }

    fun onCitiesListClick(view: View) {
        startActivity(Intent(this, CitiesListActivity::class.java))
    }
}
