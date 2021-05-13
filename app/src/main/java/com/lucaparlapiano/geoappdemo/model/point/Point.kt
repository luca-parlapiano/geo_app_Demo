package com.lucaparlapiano.geoappdemo.model.point

import com.google.gson.annotations.SerializedName

data class Point(
    @SerializedName("lat")
    val lat: String,
    @SerializedName("long")
    val long: String,

)

