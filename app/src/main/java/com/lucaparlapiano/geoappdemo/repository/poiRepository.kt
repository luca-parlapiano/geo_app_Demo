package com.lucaparlapiano.geoappdemo.repository

import androidx.lifecycle.LiveData

import com.lucaparlapiano.geoappdemo.model.poiPoint
import com.lucaparlapiano.geoappdemo.db.poiPointDao

class poiRepository(private val pointPoi: poiPointDao) {

    val readAllPoi:LiveData<List<poiPoint>> = pointPoi.getPoi()

    suspend fun addPoi(poiPoint: poiPoint){
        pointPoi.insertPoi(poiPoint)
    }


}