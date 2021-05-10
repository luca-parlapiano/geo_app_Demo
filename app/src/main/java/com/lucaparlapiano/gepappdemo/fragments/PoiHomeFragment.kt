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
        val view = inflater.inflate(R.layout.fragment_poi_home, container, false)

        return view
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
            //Config Default location
            val italy = LatLng(41.61,13.16)
            googleMap.addMarker(MarkerOptions().position(italy).title("Default marker"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(italy))
        }

        map?.setOnMapClickListener {
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(it).title("Marker"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            showDialog(it)
        }
    }

    fun showDialog(it:LatLng){
        dialogMakePoi.newInstance(it.longitude.toString(), it.latitude.toString()).show(childFragmentManager, "Tag")
    }


}