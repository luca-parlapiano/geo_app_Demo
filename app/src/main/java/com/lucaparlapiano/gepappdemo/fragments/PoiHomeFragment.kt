package com.lucaparlapiano.gepappdemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lucaparlapiano.gepappdemo.R
import kotlinx.android.synthetic.main.alert_create_poi.*
import kotlinx.android.synthetic.main.fragment_poi_home.*


class PoiHomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap:GoogleMap

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poi_home, container, false)
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
            //Config Default location
            val sydney = LatLng(-34.0, 151.0)
            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }

        map?.setOnMapClickListener {
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(it).title("Marker"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            showDialog(it)
        }
    }

    fun showDialog(it:LatLng){
        dialogMakePoi.newInstance(it.longitude.toString(), it.longitude.toString()).show(childFragmentManager, "Tag")
    }


}