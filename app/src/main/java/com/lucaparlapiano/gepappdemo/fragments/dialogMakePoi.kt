package com.lucaparlapiano.gepappdemo.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.lucaparlapiano.gepappdemo.R
import kotlinx.android.synthetic.main.alert_create_poi.*
import kotlinx.android.synthetic.main.alert_create_poi.view.*

class dialogMakePoi : DialogFragment() {

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
        view.latLong.text = arguments?.getString(KEY_LONG) +" "+ arguments?.getString(KEY_LAT)
    }

    private fun setupClickListeners(view: View) {
       view.cancelAlert.setOnClickListener{
           dismiss()
        }

        view.submitPoi.setOnClickListener{
            //Log.d("Luca", view.editTextTextPersonName.getText().toString())
            Log.d("Luca", arguments?.getString(KEY_LONG) +" "+ arguments?.getString(KEY_LAT))
        }
    }

}

