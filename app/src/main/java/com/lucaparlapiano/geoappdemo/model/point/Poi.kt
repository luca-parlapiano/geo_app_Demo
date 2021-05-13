package com.lucaparlapiano.geoappdemo.model.point

import com.google.gson.annotations.SerializedName

data class Poi(
    @SerializedName("lat")
    val lat: String,
    @SerializedName("long")
    val long: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("imgCode")
    val imgCode: String,
    )