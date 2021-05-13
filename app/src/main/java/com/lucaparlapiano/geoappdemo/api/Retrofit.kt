package com.lucaparlapiano.geoappdemo.api

import com.lucaparlapiano.geoappdemo.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: api by lazy {
        retrofit.create(api::class.java)
    }
}