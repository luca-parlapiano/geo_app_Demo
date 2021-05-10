package com.lucaparlapiano.gepappdemo.fragments

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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.lucaparlapiano.gepappdemo.R
import com.lucaparlapiano.gepappdemo.model.poiViewModel
import kotlinx.android.synthetic.main.fragment_show_poi_fragments.*


class ShowPoiFragments : Fragment(), OnMapReadyCallback {

    private val ViewModel: poiViewModel by activityViewModels()
    private lateinit var googleMap: GoogleMap

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapViewResult.onCreate(savedInstanceState)
        mapViewResult.onResume()
        mapViewResult.getMapAsync(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return inflater.inflate(R.layout.fragment_show_poi_fragments, container, false)
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
            //Config Default location
            val sydney = LatLng(-34.0, 151.0)
            val italy = LatLng(41.61,13.16)
            googleMap.addMarker(MarkerOptions().position(italy).title("Default marker"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(italy))

            ViewModel.allPoiPoint.observe(viewLifecycleOwner, Observer { poiPoint ->
                //  Log.d("ListPoint",poiPoint.toString())
                poiPoint?.let{
                    for (i in 0 until it.size) {
                        Log.d("Latitude",it[i].latitude)
                        Log.d("Longitude",it[i].longitude)
                        createMarker(it[i].latitude.toDouble(),it[i].longitude.toDouble(),it[i].name)
                    }
                }
            })
        }
    }

    protected fun createMarker(
        latitude: Double,
        longitude: Double,
        title: String?,
    ): Marker? {
        return googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
        )
    }

    /*
    * ArrayList<MarkerData> markersArray = new ArrayList<MarkerData>();

for(int i = 0 ; i < markersArray.size() ; i++) {

    createMarker(markersArray.get(i).getLatitude(), markersArray.get(i).getLongitude(), markersArray.get(i).getTitle(), markersArray.get(i).getSnippet(), markersArray.get(i).getIconResID());
}


protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {

    return googleMap.addMarker(new MarkerOptions()
            .position(new LatLng(latitude, longitude))
            .anchor(0.5f, 0.5f)
            .title(title)
            .snippet(snippet)
            .icon(BitmapDescriptorFactory.fromResource(iconResID)));
}
    *
    * */


}