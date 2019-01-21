package com.danikula.sunny.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.danikula.sunny.model.Forecast

/**
 * An adapter for displaying list of forecast entries.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private val forecasts: MutableList<Forecast> = ArrayList()

    fun swapData(newData: List<Forecast>) {
        forecasts.clear()
        forecasts.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val temperatureTextView = itemView.findViewById<TextView>(android.R.id.text1)
        private val dateTimeTextView = itemView.findViewById<TextView>(android.R.id.text2)

        fun bind(forecast: Forecast) {
            temperatureTextView.text = "${forecast.temperature} ℃"
            dateTimeTextView.text = forecast.dateTime()
        }
    }
}