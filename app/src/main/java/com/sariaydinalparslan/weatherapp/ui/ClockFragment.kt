package com.sariaydinalparslan.weatherapp.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.sariaydinalparslan.weatherapp.databinding.FragmentClockBinding
import kotlinx.android.synthetic.main.fragment_clock.*
import kotlinx.android.synthetic.main.fragment_weather.*


class ClockFragment : Fragment() {
    private var _binding: FragmentClockBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClockBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgSearchCity.setOnClickListener {
            val cityName =binding.edtCityNameClock.text.toString()
            web.webViewClient = WebViewClient()
            web.apply {
                val url = "https://www.google.com/search?q=what+time+is+it+in+"+cityName+"&sxsrf=ALiCzsaM3XvisP9rysOB-SlVu1fNtKgzLw:1667567055766&lr=lang_eng&sa=X&ved=2ahUKEwjqwv2vy5T7AhVpVfEDHfqIDN8QuAF6BAgLEAE&cshid=1667567068605383&biw=1920&bih=937&dpr=1"
                loadUrl(url)
                settings.javaScriptEnabled = true
                settings.safeBrowsingEnabled = true
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}