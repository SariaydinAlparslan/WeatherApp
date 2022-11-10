package com.sariaydinalparslan.countrylist.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sariaydinalparslan.weatherapp.R


fun ImageView.downloadfromUrl(url : String?,progressDrawable: CircularProgressDrawable){
    val options =RequestOptions()
        .placeholder(progressDrawable)
        .error(null)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}

fun placeholderProgressBar (context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

object mySingleton{

    var favourite1: String? = ""
    var favourite2: String? = ""
    var favourite3: String? = ""


}