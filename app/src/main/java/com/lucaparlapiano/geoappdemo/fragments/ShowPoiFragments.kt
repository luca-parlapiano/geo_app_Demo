package com.lucaparlapiano.geoappdemo.fragments

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.lucaparlapiano.geoappdemo.R
import com.lucaparlapiano.geoappdemo.viewModel.poiViewModel
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
            val italy = LatLng(41.61, 13.16)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(italy))

            ViewModel.allPoiPoint.observe(viewLifecycleOwner, Observer { poiPoint ->
                poiPoint?.let {
                    for (i in 0 until it.size) {
                        Log.d("image", it[i].imagUrl)
                        createMarker(
                            it[i].latitude.toDouble(),
                            it[i].longitude.toDouble(),
                            it[i].name,
                            createBitmap(it[i].imagUrl.toUri())
                        )
                    }
                }
            })
        }
    }

    fun createBitmap(urlBitmap: Uri):Bitmap {
        var bitmapTemp: Bitmap = MediaStore.Images.Media.getBitmap(
            requireActivity().contentResolver,
            urlBitmap
        )

        return Bitmap.createScaledBitmap(bitmapTemp,150,150,false)
    }

    protected fun createMarker(
        latitude: Double,
        longitude: Double,
        title: String?,
        markerImage: Bitmap
    ): Marker? {
        return googleMap.addMarker(

            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(markerImage))
            //.icon(BitmapDescriptorFactory.fromFile("file:///storage/emulated/0/Android/data/com.lucaparlapiano.gepappdemo/files/Pictures/JPEG_20210513_112543777000832479803306.jpg"))

        )
    }

}