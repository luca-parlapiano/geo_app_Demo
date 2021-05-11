package com.lucaparlapiano.gepappdemo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.lucaparlapiano.gepappdemo.R
import com.lucaparlapiano.gepappdemo.db.poiPoint
import com.lucaparlapiano.gepappdemo.model.poiViewModel
import kotlinx.android.synthetic.main.alert_create_poi.view.*


class dialogMakePoi : DialogFragment() {

    private val ViewModel: poiViewModel by activityViewModels()

    companion object {
        private const val KEY_LONG = "LONG"
        private const val KEY_LAT = "LAT"

        fun newInstance(long: String, latitude: String): dialogMakePoi {
            val args = Bundle()
            args.putString(KEY_LONG, long)
            args.putString(KEY_LAT, latitude)
            val fragment = dialogMakePoi()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.alert_create_poi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    private fun setupView(view: View) {
        //Adding round on double function
        view.latLong.text = arguments?.getString(KEY_LONG) + " " + arguments?.getString(KEY_LAT)
    }

    private fun setupClickListeners(view: View) {
        view.cancelAlert.setOnClickListener {
            dismiss()
        }

        //Show Camera for image
        view.button9.setOnClickListener {

        }

        //Show gallery per image
        view.button10.setOnClickListener {
            openGallery()
        }


        view.submitPoi.setOnClickListener {
            // Log.d("Luca", arguments?.getString(KEY_LONG) +" "+ arguments?.getString(KEY_LAT))
            var long = arguments?.getString(KEY_LONG)
            var lat = arguments?.getString(KEY_LAT)
            var name = view.editTextTextPersonName.getText().toString()

            ViewModel.insert(
                poiPoint(
                    name = name,
                    imagUrl = "",
                    longitude = long.toString(),
                    latitude = lat.toString()
                )
            )
        }

    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        // startActivityForResult(intent,ACTION_REQUEST_GALLERY)
    }

}

