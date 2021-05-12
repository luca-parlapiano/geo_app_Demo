package com.lucaparlapiano.geoappdemo.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucaparlapiano.geoappdemo.model.LocationDetail
import com.lucaparlapiano.geoappdemo.repository.LocationLiveData


class LocationViewModel (application:Application):AndroidViewModel(application) {

    var actualPosition = MutableLiveData<LocationDetail>()

    init {
        this.actualPosition.value =LocationDetail("0","0")
    }

    fun setActualPosition(locationDetail: LocationDetail){
        this.actualPosition.value=locationDetail

    }

    val actualPositionLive:LiveData<LocationDetail>
        get()=this.actualPosition

    private val locationLiveData = LocationLiveData(application)
    fun getLocationLiveData() = locationLiveData


}