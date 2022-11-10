package com.sariaydinalparslan.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sariaydinalparslan.weatherapp.model.WeatherModel
import com.sariaydinalparslan.weatherapp.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TimeDifViewModel : ViewModel() {

    private val weatherAPIService = WeatherAPIService()
    private val disposable = CompositeDisposable()

    val weather_data_dif = MutableLiveData<WeatherModel>()
    val weather_error_dif = MutableLiveData<Boolean>()
    val weather_load_dif = MutableLiveData<Boolean>()


    val weather_data_dif2 = MutableLiveData<WeatherModel>()
    val weather_error_dif2 = MutableLiveData<Boolean>()
    val weather_load_dif2 = MutableLiveData<Boolean>()


    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)

    }

    fun refreshData2(cityName: String) {
        getDataFromAPI2(cityName)

    }
    private fun getDataFromAPI(cityName: String) {
        weather_load_dif.value = true
        disposable.add(
            weatherAPIService.getDataService(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {
                    override fun onSuccess(t: WeatherModel) {
                        weather_data_dif.value = t
                        weather_error_dif.value = false
                        weather_error_dif.value = false
                    }

                    override fun onError(e: Throwable) {
                        weather_error_dif.value = true
                        weather_load_dif.value = false
                    }
                })
        )
    }

    private fun getDataFromAPI2(cityName: String) {
        weather_load_dif2.value = true
        disposable.add(
            weatherAPIService.getDataService(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {
                    override fun onSuccess(t: WeatherModel) {
                        weather_data_dif2.value = t
                        weather_error_dif2.value = false
                        weather_error_dif2.value = false
                    }

                    override fun onError(e: Throwable) {
                        weather_error_dif2.value = true
                        weather_load_dif2.value = false
                    }

                })
        )
    }
}