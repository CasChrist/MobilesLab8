package com.example.retrofitforecaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitforecaster.R

class WeatherAdapter :
    ListAdapter<WeatherItem, WeatherAdapter.WeatherViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tempText: TextView = itemView.findViewById(R.id.temp)
        private val timeText: TextView = itemView.findViewById(R.id.time)

        fun bind(item: WeatherItem) {
            tempText.text = "${item.main.temp} °C"
            timeText.text = item.dt_txt
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<WeatherItem>() {
        override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean =
            oldItem.dt_txt == newItem.dt_txt

        override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean =
            oldItem == newItem
    }
}
