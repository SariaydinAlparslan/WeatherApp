package com.sariaydinalparslan.weatherapp.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sariaydinalparslan.countrylist.util.mySingleton
import com.sariaydinalparslan.weatherapp.R
import com.sariaydinalparslan.weatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GET = this.requireActivity().getSharedPreferences("com.sariaydinalparslan.weatherapp", AppCompatActivity.MODE_PRIVATE)!!

        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var cName = GET.getString("cityName","ankara")
        edt_city_name.setText(cName)
        viewModel.refreshData(cName!!)

        getLiveData()

        swipe_refresh_layout.setOnRefreshListener {
            ll_data.visibility=View.GONE
            pb_loading.visibility = View.GONE
            tv_error.visibility = View.GONE

            var cityName = GET.getString("citytName",cName)
            edt_city_name.setText(cityName)
            viewModel.refreshData(cityName!!)
            swipe_refresh_layout.isRefreshing=false
        }
        img_search_city.setOnClickListener {
            lottie1.visibility = View.INVISIBLE
            desciription.visibility = View.INVISIBLE
            humidity.visibility = View.INVISIBLE
           // pressure.visibility = View.INVISIBLE
            feelslike.visibility = View.INVISIBLE
            winds.visibility = View.INVISIBLE

            val cityName = edt_city_name.text.toString()
            SET.putString("cityName",cityName)
            SET.apply()
            viewModel.refreshData(cityName)
            getLiveData()
        }
        information.setOnClickListener {
            desciription.visibility = View.VISIBLE
            humidity.visibility = View.VISIBLE
            //pressure.visibility = View.VISIBLE
            feelslike.visibility = View.VISIBLE
            winds.visibility = View.VISIBLE
        }
        addFavourite1.setOnClickListener {
            lottie1.visibility = View.VISIBLE
            lottie1.playAnimation()
            mySingleton.favourite1 = tv_city_name.text.toString()

        }
        addFavourite2.setOnClickListener {
            lottie1.visibility = View.VISIBLE
            lottie1.playAnimation()
            mySingleton.favourite2 = tv_city_name.text.toString()

        }
        addFavourite3.setOnClickListener {
            lottie1.visibility = View.VISIBLE
            lottie1.playAnimation()
            mySingleton.favourite3 = tv_city_name.text.toString()

        }

    }
    @SuppressLint("SetTextI18n")
    private fun getLiveData() {
        viewModel.weather_data.observe(requireActivity(), Observer { data ->
            data.let {
                ll_data.visibility=View.VISIBLE
                pb_loading.visibility = View.GONE
                tv_degree.text= data.main.temp.toInt().toString() + "°C"
                tv_city_name.text = data.name
                desciription.text = data.weather[0].description
                humidity.text = "Humidity : "+data.main.humidity.toString()
                //pressure.text = "Pressure : "+data.main.pressure.toString()
                feelslike.text = "FeelsLike : "+data.main.feelsLike.toString()
                winds.text = "Winds Speed : "+data.wind.speed.toString()
                // tv_lat.text =" :"+ data.coord.lat.toString()
                // tv_lon.text=" :"+ data.coord.lon.toString()

                val icon =  data.weather[0].icon

                if (icon == "01d"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.day_clear)
                }
                if (icon == "02d"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.cloud_2)
                }
                if (icon == "03d"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.clouds_day)
                }
                if (icon == "04d"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.clouds_day)
                }
                if (icon == "09d"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.raining_day)
                }
                if (icon == "10d"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.raining_day)
                }
                if (icon == "11d"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.thunder_night)
                }
                if (icon == "13d"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.snow_day)
                }
                if (icon == "50d"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.mist)
                }
                if (icon == "01n"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.night_clear)
                }
                if (icon == "02n"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.clouds_night)
                }
                if (icon == "03n"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.clouds_night)
                }
                if (icon == "04n"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.cloud_2_night)
                }
                if (icon == "09n"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.raining_night)
                }
                if (icon == "10n"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.raining_night)
                }
                if (icon == "11n"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.thunder_night)
                }
                if (icon == "13n"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.snow_night)
                }
                if (icon == "50n"){
                    background.background = ContextCompat.getDrawable(requireContext(), R.drawable.mist)
                }
            }
        })
        viewModel.weather_load.observe(requireActivity(), Observer{ load->
            load?.let {
                if (it){
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility= View.GONE
                    ll_data.visibility = View.GONE
                }else{
                    pb_loading.visibility = View.GONE
                }
            }
        })
        viewModel.weather_error.observe(requireActivity(), Observer { error ->
            error?.let {
                if (it){
                    pb_loading.visibility = View.GONE
                    //  tv_error.visibility= View.VISIBLE
                    ll_data.visibility = View.VISIBLE
                }else{
                    // tv_error.visibility= View.GONE
                }
            }

        })
    }

}