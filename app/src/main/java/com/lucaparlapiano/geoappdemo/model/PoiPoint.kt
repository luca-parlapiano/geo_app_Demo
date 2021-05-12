package com.lucaparlapiano.geoappdemo.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "poiPoint")
data class poiPoint(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val imagUrl: String,
    val longitude:String,
    val latitude:String
    )