package com.lucaparlapiano.geoappdemo.repository

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.lucaparlapiano.geoappdemo.Constants.Companion.ONE_MINUTE
import com.lucaparlapiano.geoappdemo.model.LocationDetail

class LocationLiveData (application: Application): LiveData<LocationDetail>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation.addOnSuccessListener {
            location:Location -> location.also {
            setLocationData(it)
        }}
        startLocationUpdate()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private val locationCallback = object :LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            for(location in locationResult.locations){
                setLocationData(location)
            }
        }
    }

    private fun setLocationData(location: Location) {
        value = LocationDetail(location.latitude.toString(),location.longitude.toString())
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object{
        val locationRequest : LocationRequest = LocationRequest.create().apply {
            interval = ONE_MINUTE
            fastestInterval = ONE_MINUTE/4
            priority= LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    }
}