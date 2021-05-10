package com.lucaparlapiano.gepappdemo.repository

import androidx.lifecycle.LiveData

import com.lucaparlapiano.gepappdemo.db.poiPoint
import com.lucaparlapiano.gepappdemo.db.poiPointDao

class poiRepository(private val pointPoi: poiPointDao) {

    val readAllPoi:LiveData<List<poiPoint>> = pointPoi.getPoi()

    suspend fun addPoi(poiPoint: poiPoint){
        pointPoi.insertPoi(poiPoint)
    }
}