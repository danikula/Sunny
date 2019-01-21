package com.danikula.sunny.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.danikula.sunny.R
import com.danikula.sunny.model.City


/**
 * An adapter for displaying list of cities.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
class CityAdapter(private val clickListener: (City) -> Unit) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val cities: MutableList<City> = ArrayList()

    fun swapData(newData: List<City>) {
        cities.clear()
        cities.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.simple_list_item, parent, false)
        return CityViewHolder(itemView, clickListener)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.bind(city)
    }

    fun deleteCity(position: Int): City {
        val city = cities[position]
        cities.removeAt(position)
        notifyItemRemoved(position)
        return city
    }

    class CityViewHolder(itemView: View, private val clickListener: (City) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val textView = itemView.findViewById<TextView>(R.id.textView)

        fun bind(city: City) {
            textView.isClickable = true
            textView.text = city.nameWithCountry()
            textView.setOnClickListener { clickListener(city) }
        }
    }
}