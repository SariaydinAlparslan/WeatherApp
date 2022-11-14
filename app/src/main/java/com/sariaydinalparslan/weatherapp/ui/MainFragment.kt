package com.sariaydinalparslan.weatherapp.ui

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.sariaydinalparslan.weatherapp.R
import com.sariaydinalparslan.weatherapp.databinding.FragmentMainBinding
import com.sariaydinalparslan.weatherapp.viewmodel.MainFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_weather.*

class MainFragment : Fragment() {
    private lateinit var mainViewModel: MainFragmentViewModel
    lateinit var mAdView : AdView
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    var favourite1 : String? = null
    var favourite2 : String? = null
    var favourite3 : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MobileAds.initialize(requireContext()) {}

        mAdView =binding.adView

        adMobBanner()
        adMobInter()
        //ca-app-pub-3940256099942544/1033173712


        mainViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)

        val sharedPreferences = this.activity?.getSharedPreferences("com.sariaydinalparslan.weatherapp",
            MODE_PRIVATE)
        favourite1 = sharedPreferences!!.getString("favori1","")
        favourite2 = sharedPreferences!!.getString("favori2","")
        favourite3 = sharedPreferences!!.getString("favori3","")

        getLiveData()


        if (favourite1 !==""){
            circle.visibility = View.GONE
            circle_.visibility = View.VISIBLE
            mainViewModel.refreshData(favourite1.toString())
            circle_image1.visibility = View.VISIBLE
        }
        if (favourite2!==""){
            circle1.visibility = View.GONE
            circle1_1.visibility = View.VISIBLE
            mainViewModel.refreshData2(favourite2.toString())
            circle_image2.visibility = View.VISIBLE
        }
        if (favourite3!==""){
            circle2.visibility = View.GONE
            circle2_2.visibility = View.VISIBLE
            mainViewModel.refreshData3(favourite3.toString())
            circle_image3.visibility = View.VISIBLE
        }


        weather.setOnClickListener {

            val action = MainFragmentDirections.actionWeather()
            Navigation.findNavController(it).navigate(action)

            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.e("alp", "The interstitial ad wasn't ready yet.")
            }
        }
        clock.setOnClickListener {
            val action = MainFragmentDirections.actionClock()
            Navigation.findNavController(it).navigate(action)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.e("alp", "The interstitial ad wasn't ready yet.")
            }
        }
        time.setOnClickListener {
            val action = MainFragmentDirections.actiontime()
            Navigation.findNavController(it).navigate(action)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.e("alp", "The interstitial ad wasn't ready yet.")
            }
        }
        circle.setOnClickListener {
            val action = MainFragmentDirections.actionWeather()
            Navigation.findNavController(it).navigate(action)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.e("alp", "The interstitial ad wasn't ready yet.")
            }
        }
        circle2.setOnClickListener {
            val action = MainFragmentDirections.actionWeather()
            Navigation.findNavController(it).navigate(action)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.e("alp", "The interstitial ad wasn't ready yet.")
            }
        }
        circle1.setOnClickListener {
            val action = MainFragmentDirections.actionWeather()
            Navigation.findNavController(it).navigate(action)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.e("alp", "The interstitial ad wasn't ready yet.")
            }
        }
    }
    private fun adMobInter() {
        val adRequest = AdRequest.Builder().build()

        mAdView.loadAd(adRequest)

        InterstitialAd.load(requireContext(),"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    @SuppressLint("SetTextI18n")
    private fun getLiveData() {
        mainViewModel.weather_data_main.observe(requireActivity(), Observer { data ->
            data.let {
                circle_.text = data.main.temp.toInt().toString() + "°C"+ " " +  data.name
               Log.e("alp",data.weather[0].icon)

             val icon = data.weather[0].icon
                if (icon == "01d"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d012x))
                }
                if (icon == "02d"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d022x))
                }
                if (icon == "03d"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d032x))
                }
                if (icon == "04d"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d042x))
                }
                if (icon == "09d"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d092x))
                }
                if (icon == "10d"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d102x))
                }
                if (icon == "11d"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d112x))
                }
                if (icon == "13d"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d132x))
                }
                if (icon == "50d"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d502x))
                }
                if (icon == "01n"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n012x))
                }
                if (icon == "02n"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n022x))
                }
                if (icon == "03n"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n032x))
                }
                if (icon == "04n"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n042x))
                }
                if (icon == "09n"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n092x))
                }
                if (icon == "10n"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n102x))
                }
                if (icon == "11n"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n112x))
                }
                if (icon == "13n"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n132x))
                }
                if (icon == "50n"){
                    circle_image1.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n502x))
                }
            }
        })
        mainViewModel.weather_data_main2.observe(requireActivity(), Observer { data ->
            data.let {
                circle1_1.text = data.main.temp.toInt().toString() + "°C" + " " + data.name
                val icon = data.weather[0].icon
                if (icon == "01d"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d012x))
                }
                if (icon == "02d"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d022x))
                }
                if (icon == "03d"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d032x))
                }
                if (icon == "04d"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d042x))
                }
                if (icon == "09d"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d092x))
                }
                if (icon == "10d"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d102x))
                }
                if (icon == "11d"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d112x))
                }
                if (icon == "13d"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d132x))
                }
                if (icon == "50d"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d502x))
                }
                if (icon == "01n"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n012x))
                }
                if (icon == "02n"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n022x))
                }
                if (icon == "03n"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n032x))
                }
                if (icon == "04n"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n042x))
                }
                if (icon == "09n"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n092x))
                }
                if (icon == "10n"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n102x))
                }
                if (icon == "11n"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n112x))
                }
                if (icon == "13n"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n132x))
                }
                if (icon == "50n"){
                    circle_image2.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n502x))
                }
            }
        })
        mainViewModel.weather_data_main3.observe(requireActivity(), Observer { data ->
            data.let {
                circle2_2.text = data.main.temp.toInt().toString() + "°C" + " " +  data.name
                val icon = data.weather[0].icon
                if (icon == "01d"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d012x))
                }
                if (icon == "02d"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d022x))
                }
                if (icon == "03d"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d032x))
                }
                if (icon == "04d"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d042x))
                }
                if (icon == "09d"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d092x))
                }
                if (icon == "10d"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d102x))
                }
                if (icon == "11d"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d112x))
                }
                if (icon == "13d"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d132x))
                }
                if (icon == "50d"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.d502x))
                }
                if (icon == "01n"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n012x))
                }
                if (icon == "02n"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n022x))
                }
                if (icon == "03n"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n032x))
                }
                if (icon == "04n"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n042x))
                }
                if (icon == "09n"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n092x))
                }
                if (icon == "10n"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n102x))
                }
                if (icon == "11n"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n112x))
                }
                if (icon == "13n"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n132x))
                }
                if (icon == "50n"){
                    circle_image3.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.n502x))
                }
            }
        })
    }
    private fun adMobBanner(){
        mAdView.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                Toast.makeText(requireContext(), "reklam yüklendi", Toast.LENGTH_SHORT).show()
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }
    }

}