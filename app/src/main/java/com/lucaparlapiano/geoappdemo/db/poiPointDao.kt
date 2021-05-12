package com.lucaparlapiano.geoappdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lucaparlapiano.geoappdemo.model.poiPoint

@Dao
interface poiPointDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPoi(point: poiPoint)

    @Query("SELECT * FROM poiPoint ORDER BY id ASC")
    fun getPoi(): LiveData<List<poiPoint>>
}