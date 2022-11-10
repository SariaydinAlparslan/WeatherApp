package com.sariaydinalparslan.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.sariaydinalparslan.countrylist.util.mySingleton
import com.sariaydinalparslan.weatherapp.R
import com.sariaydinalparslan.weatherapp.viewmodel.MainFragmentViewModel
import com.sariaydinalparslan.weatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_weather.*

class MainFragment : Fragment() {
    private lateinit var mainViewModel: MainFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)

        getLiveData()

        if (mySingleton.favourite1!==""){
            circle.visibility = View.GONE
            circle_.visibility = View.VISIBLE
            mainViewModel.refreshData(mySingleton.favourite1.toString())
        }
        if (mySingleton.favourite2!==""){
            circle1.visibility = View.GONE
            circle1_1.visibility = View.VISIBLE
            mainViewModel.refreshData2(mySingleton.favourite2.toString())
        }
        if (mySingleton.favourite3!==""){
            circle2.visibility = View.GONE
            circle2_2.visibility = View.VISIBLE
            mainViewModel.refreshData3(mySingleton.favourite3.toString())
        }

        weather.setOnClickListener {
            val action = MainFragmentDirections.actionWeather()
            Navigation.findNavController(it).navigate(action)
        }
        clock.setOnClickListener {
            val action = MainFragmentDirections.actionClock()
            Navigation.findNavController(it).navigate(action)
        }
        time.setOnClickListener {
            val action = MainFragmentDirections.actiontime()
            Navigation.findNavController(it).navigate(action)
        }
        circle.setOnClickListener {
            val action = MainFragmentDirections.actiontime()
            Navigation.findNavController(it).navigate(action)
        }
        circle2.setOnClickListener {
            val action = MainFragmentDirections.actionWeather()
            Navigation.findNavController(it).navigate(action)
        }
        circle1.setOnClickListener {
            val action = MainFragmentDirections.actionWeather()
            Navigation.findNavController(it).navigate(action)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getLiveData() {
        mainViewModel.weather_data_main.observe(requireActivity(), Observer { data ->
            data.let {
                circle_.text = data.main.temp.toInt().toString() + "°C"+ " " +  data.name
            }
        })
        mainViewModel.weather_data_main2.observe(requireActivity(), Observer { data ->
            data.let {
                circle1_1.text = data.main.temp.toInt().toString() + "°C" + " " + data.name
            }
        })
        mainViewModel.weather_data_main3.observe(requireActivity(), Observer { data ->
            data.let {
                circle2_2.text = data.main.temp.toInt().toString() + "°C" + " " +  data.name
            }
        })
    }
}