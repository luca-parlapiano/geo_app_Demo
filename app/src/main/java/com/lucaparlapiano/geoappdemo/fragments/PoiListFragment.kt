package com.lucaparlapiano.geoappdemo.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucaparlapiano.geoappdemo.R
import com.lucaparlapiano.geoappdemo.adapters.RecycledViewAdapter
import com.lucaparlapiano.geoappdemo.viewModel.poiViewModel
import kotlinx.android.synthetic.main.fragment_poi_list.*
import kotlinx.android.synthetic.main.fragment_poi_list.view.*


class PoiListFragment : Fragment() {

    private val ViewModel: poiViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listaPoi.layoutManager = LinearLayoutManager(requireContext())

        ViewModel.allPoiPoint.observe(requireActivity(), Observer { poiPoint ->
            poiPoint?.let{
                //Log.d("ListFragment",it.toString())
                var listaPoint = it
                listaPoi?.let{
                    listaPoi.adapter = RecycledViewAdapter(listaPoint)
                }

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