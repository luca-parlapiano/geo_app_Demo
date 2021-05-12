package com.lucaparlapiano.geoappdemo.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class LocationViewModel (application:Application):AndroidViewModel(application) {

    private val locationLiveData = LocationLiveData(application)
    fun getLocationLiveData() = locationLiveData

}