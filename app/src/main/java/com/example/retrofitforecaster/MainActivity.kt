package com.example.retrofitforecaster

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: WeatherAdapter
    private lateinit var cityNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Установка фона на бледно-оранжевый цвет
        window.decorView.setBackgroundColor(Color.parseColor("#FFCC99"))

        // Инициализация TextView для названия города
        cityNameTextView = findViewById(R.id.city_name)
        adapter = WeatherAdapter()
        findViewById<RecyclerView>(R.id.r_view).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        fetchWeather()
    }

    private fun fetchWeather() {
        val apiKey = "29a24e313af4439bd524fa07c6421932"
        val city = "Shklov" // Название города

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getWeatherForecast(city, apiKey)
                val weatherItems = response.list.map { weather ->
                    val iconCode = weather.weather[0].icon
                    // Преобразование температуры из Кельвинов в Цельсий и округление до целого числа
                    val temperature = "${(weather.main.temp - 273.15).toInt()}°C" // Форматируем температуру

                    // Получение времени и даты
                    val dateTime = java.util.Date(weather.dt * 1000L) // Преобразование времени из Unix в Date
                    val dateFormat = java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                    val timeFormat = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())

                    val date = dateFormat.format(dateTime)
                    val time = timeFormat.format(dateTime)

                    val iconUrl = "http://openweathermap.org/img/wn/$iconCode@2x.png"
                    WeatherItem(temperature, iconUrl, time, date)
                }
                adapter.submitList(weatherItems)

                // Установка названия города в TextView
                cityNameTextView.text = city

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
            }
        }
    }
}