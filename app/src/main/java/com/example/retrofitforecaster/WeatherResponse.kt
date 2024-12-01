package com.example.retrofitforecaster

data class WeatherResponse(
    val list: List<WeatherItem>
)

data class WeatherItem(
    val dt_txt: String,
    val main: MainInfo,
    val weather: List<WeatherDescription>
)

data class MainInfo(
    val temp: Double
)

data class WeatherDescription(
    val main: String
)
