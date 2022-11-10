package com.sariaydinalparslan.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sariaydinalparslan.weatherapp.model.WeatherModel
import com.sariaydinalparslan.weatherapp.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainFragmentViewModel: ViewModel() {
    private val weatherAPIService = WeatherAPIService()
    private val disposable = CompositeDisposable()

    val weather_data_main = MutableLiveData<WeatherModel>()
    val weather_error_main = MutableLiveData<Boolean>()
    val weather_load_main = MutableLiveData<Boolean>()

    val weather_data_main2 = MutableLiveData<WeatherModel>()
    val weather_error_main2 = MutableLiveData<Boolean>()
    val weather_load_main2 = MutableLiveData<Boolean>()
    val weather_data_main3 = MutableLiveData<WeatherModel>()
    val weather_error_main3 = MutableLiveData<Boolean>()
    val weather_load_main3 = MutableLiveData<Boolean>()


    fun refreshData(cityName:String){
        getDataFromAPI(cityName)

    }
    fun refreshData2(cityName:String){
        getDataFromAPI2(cityName)

    }
    fun refreshData3(cityName:String){
        getDataFromAPI3(cityName)

    }
    private fun getDataFromAPI(cityName : String) {
        weather_load_main.value=true
        disposable.add(
            weatherAPIService.getDataService(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(t: WeatherModel) {
                        weather_data_main.value= t
                        weather_error_main.value=false
                        weather_error_main.value=false
                    }
                    override fun onError(e: Throwable) {
                        weather_error_main.value = true
                        weather_load_main.value = false

                    }

                })
        )
    }
    private fun getDataFromAPI2(cityName : String) {
        weather_load_main2.value=true
        disposable.add(
            weatherAPIService.getDataService(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(t: WeatherModel) {
                        weather_data_main2.value= t
                        weather_error_main2.value=false
                        weather_error_main2.value=false
                    }
                    override fun onError(e: Throwable) {
                        weather_error_main2.value = true
                        weather_load_main2.value = false
                    }
                })
        )
    }
    private fun getDataFromAPI3(cityName : String) {
        weather_load_main3.value=true
        disposable.add(
            weatherAPIService.getDataService(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(t: WeatherModel) {
                        weather_data_main3.value= t
                        weather_error_main3.value=false
                        weather_error_main3.value=false
                    }
                    override fun onError(e: Throwable) {
                        weather_error_main3.value = true
                        weather_load_main3.value = false

                    }

                })
        )
    }
}