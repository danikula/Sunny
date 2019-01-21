package com.danikula.sunny.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.danikula.sunny.R
import com.danikula.sunny.viewmodel.ForecastViewModel

class ForecastActivity : AppCompatActivity() {

    private lateinit var viewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        viewModel = ViewModelProviders.of(this).get(ForecastViewModel::class.java)
    }
}
