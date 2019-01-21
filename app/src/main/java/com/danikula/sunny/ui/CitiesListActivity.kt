package com.danikula.sunny.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.danikula.sunny.R
import com.danikula.sunny.di.InjectorFactory
import com.danikula.sunny.model.City
import com.danikula.sunny.viewmodel.CitiesListViewModel
import com.danikula.sunny.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*
import javax.inject.Inject

class CitiesListActivity : AppCompatActivity(), CityAdapter.OnCityClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CitiesListViewModel
    private val adapter = CityAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorFactory.newAppInjector(application).inject(this)
        initViewModel()
        initUi()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CitiesListViewModel::class.java)
        viewModel.cities.observe(this, Observer {
            adapter.swapData(it ?: Arrays.asList())
        })
    }

    private fun initUi() {
        setContentView(R.layout.activity_cities_list)
        recyclerView.setup(adapter)
    }

    override fun onCityClick(city: City) {
        viewModel.onCitySelected(city)
    }
}
