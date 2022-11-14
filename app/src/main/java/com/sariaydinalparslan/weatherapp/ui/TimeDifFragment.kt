package com.sariaydinalparslan.weatherapp.ui

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sariaydinalparslan.countrylist.util.mySingleton
import com.sariaydinalparslan.weatherapp.R
import com.sariaydinalparslan.weatherapp.viewmodel.MainViewModel
import com.sariaydinalparslan.weatherapp.viewmodel.TimeDifViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_time_dif.*
import kotlinx.android.synthetic.main.fragment_weather.*
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class TimeDifFragment : Fragment() {

    private lateinit var timeViewModel: TimeDifViewModel
    private var long1: Int = 0
    private  var long2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_time_dif, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timeViewModel = ViewModelProviders.of(this).get(TimeDifViewModel::class.java)

        ready.setOnClickListener {
            if (edt_city_name1.text!!.isEmpty() || edt_city_name2.text!!.isEmpty()){
                picktoast()
            }else{
                ready.visibility = View.INVISIBLE
                calculate_btn.visibility = View.VISIBLE
                timedif_result1.visibility = View.INVISIBLE

                val cityName = edt_city_name1.text.toString()
                val cityName2= edt_city_name2.text.toString()

                timeViewModel.refreshData(cityName)
                timeViewModel.refreshData2(cityName2)

            }
        }
        calculate_btn.setOnClickListener {

            ready.visibility = View.VISIBLE
            calculate_btn.visibility = View.INVISIBLE
            timedif_result1.visibility = View.VISIBLE

            getLiveData()

            lottie_clock.playAnimation()

            val hand = Handler()
            hand.postDelayed( {
                val resultMinute  =  if (long1 >= long2){
                    (long1-long2) * 4
                }else{
                    (long2-long1) * 4
                }

                timedif_result1.text = resultMinute.toString()+"  "+ "Minutes"
            },1000)

        }
    }
    private fun getLiveData() {
        timeViewModel.weather_data_dif.observe(requireActivity(), Observer { data ->
        data.let {
          long1 = data.coord.lon.toInt()
        }
    })
        timeViewModel.weather_data_dif2.observe(requireActivity(), Observer { data ->
            data.let {
            long2  = data.coord.lon.toInt()
            }
        })
    }
    private fun picktoast(){
        MotionToast.darkToast(
            requireContext() as Activity, getString(R.string.blank),"",
            MotionToastStyle.INFO,
            MotionToast.GRAVITY_CENTER,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
    }

}