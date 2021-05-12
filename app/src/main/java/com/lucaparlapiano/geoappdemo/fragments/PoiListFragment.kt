package com.lucaparlapiano.geoappdemo.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.lucaparlapiano.geoappdemo.R
import com.lucaparlapiano.geoappdemo.viewModel.poiViewModel


class PoiListFragment : Fragment() {

    private val ViewModel: poiViewModel by activityViewModels()
    /*
    * WARNING
    * this fragment is't used
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModel.allPoiPoint.observe(this, Observer { poiPoint ->
            poiPoint?.let{
                Log.d("ListFragment",it.toString())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_poi_list, container, false)
        return view
    }

}