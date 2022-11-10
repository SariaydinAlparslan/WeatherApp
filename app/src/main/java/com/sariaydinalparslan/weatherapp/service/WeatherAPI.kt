package com.sariaydinalparslan.weatherapp.service

import com.sariaydinalparslan.weatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/weather?id=524901&appid=38d0ff0e40462ac440fc6c01445f7250
//https://api.openweathermap.org/data/2.5/weather?q=izmir&APPID=38d0ff0e40462ac440fc6c01445f7250
interface WeatherAPI {
    @GET("data/2.5/weather?&units=metric&APPID=38d0ff0e40462ac440fc6c01445f7250")
    fun getData(
        @Query("q") cityName:String
    ): Single<WeatherModel>
}