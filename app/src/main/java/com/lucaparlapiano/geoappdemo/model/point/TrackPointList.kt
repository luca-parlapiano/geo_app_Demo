package com.lucaparlapiano.geoappdemo.model.point

import com.google.gson.annotations.SerializedName

data class TrackPointList(
    @SerializedName("results")
    val trackPoins: List<Point>
)
