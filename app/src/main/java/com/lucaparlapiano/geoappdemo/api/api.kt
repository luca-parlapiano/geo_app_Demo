package com.lucaparlapiano.geoappdemo.api

import com.lucaparlapiano.geoappdemo.model.point.OkResponse
import com.lucaparlapiano.geoappdemo.model.point.Poi
import com.lucaparlapiano.geoappdemo.model.point.Point
import com.lucaparlapiano.geoappdemo.model.point.TrackPointList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface api {

    //This Url mock save Single user Poi
    @GET("poi/save")
    suspend fun savePoi(
        @Query("userId") userId:String,
        @Query("name") name:String,
        @Query("latitude")latitude:String,
        @Query("longitude")longitude:String,
        @Query("imgCode") imgCode:String
    ):Response<OkResponse>


    //This Url mock receive All poi From Server Track data at server
    @GET("poi/list")
    suspend fun getPoiList(
        @Query("userId") userId:String,
    ):Response<List<Point>>

    //Url for Sand track data
    @GET("trackUserData")
    suspend fun sendTrackData(
        @Query("userId") userId:String,
        @Query("point") point:List<Point>
    ):Response<OkResponse>
}