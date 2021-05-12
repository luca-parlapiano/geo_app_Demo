package com.lucaparlapiano.geoappdemo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.lucaparlapiano.geoappdemo.db.AppDatabase
import com.lucaparlapiano.geoappdemo.model.poiPoint
import com.lucaparlapiano.geoappdemo.repository.poiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class poiViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: poiRepository
    val allPoiPoint: LiveData<List<poiPoint>>

    init {
        val database =
            AppDatabase.getDatabase(application.applicationContext, viewModelScope)?.poiPointDao()
        repository = poiRepository(database!!)
        allPoiPoint = repository.readAllPoi
    }

    fun insert(poiPo: poiPoint) = viewModelScope.launch(Dispatchers.IO) {
        repository.addPoi(poiPo)
    }

}
