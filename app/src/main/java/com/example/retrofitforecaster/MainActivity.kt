package com.example.retrofitforecaster

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = WeatherAdapter()
        findViewById<RecyclerView>(R.id.r_view).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        fetchWeather()
    }

    private fun fetchWeather() {
        val apiKey = "29a24e313af4439bd524fa07c6421932"

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getWeatherForecast("Shklov", apiKey)
                adapter.submitList(response.list)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
