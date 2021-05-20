package com.lucaparlapiano.geoappdemo.adapters

import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucaparlapiano.geoappdemo.R
import com.lucaparlapiano.geoappdemo.model.poiPoint
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.poi_list_single.view.*


class RecycledViewAdapter(private val poiList: List<poiPoint>) :
    RecyclerView.Adapter<myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val singleItem: View = inflater.inflate(R.layout.poi_list_single, parent, false)
        return myViewHolder(singleItem)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.bind(poiList[position])
    }

    override fun getItemCount(): Int {
        return poiList.size
    }
}

class myViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(poiPoint: poiPoint) {

        view.TitleText.text = poiPoint.name
        view.LatitudeTextView.text = poiPoint.latitude
        view.LongitudeTextView.text = poiPoint.longitude
        Picasso.get().load(poiPoint.imagUrl).into(view.ImageViewPoi);


    }

}
