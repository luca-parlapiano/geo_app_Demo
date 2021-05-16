package com.lucaparlapiano.geoappdemo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lucaparlapiano.geoappdemo.R
import com.lucaparlapiano.geoappdemo.viewModel.LocationViewModel
import kotlinx.android.synthetic.main.fragment_poi_home.*


class PoiHomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private val locationViewModel: LocationViewModel by activityViewModels()

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
            googleMap.clear()
            locationViewModel.actualPositionLive.observe(requireActivity(), Observer {
                if (!(it.latitude == it.longitude)) {
                    //Exact User Position

                    Log.d("Position Real", it.latitude + " - " + it.longitude)
                    val userPosition = LatLng(it.latitude.toDouble(), it.longitude.toDouble())

                    googleMap.addMarker(
                        MarkerOptions().position(userPosition)
                            .title("Your Position")
                    )
                    val yourLocation = CameraUpdateFactory.newLatLng(userPosition)

                     googleMap.moveCamera(yourLocation)
                    // Animation is Fun!
                    //val yourLocation = CameraUpdateFactory.newLatLngZoom(userPosition, 10f)
                   // googleMap.animateCamera(yourLocation)
                } else {
                    /*
                   googleMap.clear()
                    //Config Default location
                    Log.d("Position Default", it.latitude + " - " + it.longitude)
                    val italy = LatLng(41.61, 13.16)
                    googleMap.addMarker(
                        MarkerOptions().position(italy).title(getString(R.string.noGPS))
                    )
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(italy))*/
                }

            })

        }

        map?.setOnMapClickListener {
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(it).title("Marker"))
            googleMap.clear()
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            showDialog(it)
        }
    }

    fun showDialog(it: LatLng) {
        dialogMakePoi.newInstance(it.longitude.toString(), it.latitude.toString())
            .show(childFragmentManager, "Tag")
    }

    override fun onDetach() {
        super.onDetach()
        locationViewModel.actualPositionLive.removeObservers(this)
    }

}