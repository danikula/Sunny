package com.danikula.sunny.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.danikula.sunny.R
import com.danikula.sunny.di.InjectorFactory
import com.danikula.sunny.model.City
import com.danikula.sunny.ui.adapter.CityAdapter
import com.danikula.sunny.ui.adapter.support.SwipeToDeleteCallback
import com.danikula.sunny.viewmodel.CitiesListViewModel
import com.danikula.sunny.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*
import javax.inject.Inject


class CitiesListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CitiesListViewModel
    private val adapter = CityAdapter(this::onCityClick)

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
        enableSwipeToDelete()
    }

    private fun enableSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val deletedCity = adapter.deleteCity(viewHolder.adapterPosition)
                viewModel.deleteCity(deletedCity)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun onCityClick(city: City) {
        viewModel.onCitySelected(city)
        val intent = Intent(this, ForecastActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
