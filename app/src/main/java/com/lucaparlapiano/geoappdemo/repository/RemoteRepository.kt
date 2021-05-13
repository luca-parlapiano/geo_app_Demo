package com.lucaparlapiano.geoappdemo.repository

import com.lucaparlapiano.geoappdemo.api.Retrofit
import com.lucaparlapiano.geoappdemo.model.point.OkResponse
import com.lucaparlapiano.geoappdemo.model.point.Poi
import com.lucaparlapiano.geoappdemo.model.point.Point
import retrofit2.Response

class RemoteRepository {

    suspend fun getPoi(
        userId:String,
        name:String,
        latitude:String,
        longitude:String,
        imgCode:String
    ):Response<OkResponse>{
        return Retrofit.api.savePoi(
            userId,
            name,
            latitude,
            longitude,
            imgCode
        )
    }

    suspend fun getPoiList(
        userId:String,
    ):Response<List<Point>>{
        return Retrofit.api.getPoiList(userId)
    }

    suspend fun sendTrackData(
        userId:String,
        trackPoint:List<Point>
    ):Response<OkResponse>{
        return Retrofit.api.sendTrackData(userId,trackPoint)
    }
}