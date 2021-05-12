package com.lucaparlapiano.geoappdemo.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lucaparlapiano.geoappdemo.Constants
import com.lucaparlapiano.geoappdemo.Constants.FINE_LOCATION
import com.lucaparlapiano.geoappdemo.R
import com.lucaparlapiano.geoappdemo.repository.LocationViewModel
import kotlinx.android.synthetic.main.fragment_poi_home.*
import java.util.jar.Manifest


class PoiHomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap:GoogleMap
    private lateinit var locationViewModel: LocationViewModel

    private var GPSlat:String = ""
    private var GPSlong:String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
        requestlocationUpgrade()
    }

    //Nel caso avesse detto di no nella home richiedo almeno il permesso  per ottenere la posizione

    private fun requestlocationUpgrade() {
       if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
           locationUpgrade()
       }else{
           val permissionRequest = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
           requestPermissions(permissionRequest,FINE_LOCATION)
       }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            Constants.FINE_LOCATION ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   // locationUpgrade()
                }else{
                    Toast.makeText(context,"Check GPS permission",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun locationUpgrade() {
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        locationViewModel.getLocationLiveData().observe(requireActivity(), Observer {
            Log.d("Position Source INT",it.latitude+" - "+it.longitude)
        })
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
           // val italy = LatLng(41.61,13.16)
            locationViewModel.getLocationLiveData().observe(requireActivity(), Observer {
                Log.d("Position Source ",it.latitude+" - "+it.longitude)
                val userPosition = LatLng(it.longitude.toDouble(),it.latitude.toDouble())
                googleMap.clear()
                googleMap.addMarker(
                    MarkerOptions().
                    position(userPosition).
                    title("User Position"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(userPosition))
            })
            /*
            googleMap.addMarker(MarkerOptions().position(italy).title("Default marker"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(italy))
            */

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