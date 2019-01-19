package com.danikula.sunny.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.danikula.sunny.model.City

/**
 * An adapter for displaying list of cities.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
class CityAdapter : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val cities: MutableList<City> = ArrayList()

    fun swapData(newData: List<City>) {
        cities.clear()
        cities.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return CityViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.bind(city)
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView = itemView.findViewById<TextView>(android.R.id.text1)

        fun bind(city: City) {
            textView.text = city.nameWithCountry()
        }
    }
}