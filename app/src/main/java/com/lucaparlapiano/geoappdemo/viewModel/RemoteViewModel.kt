package com.lucaparlapiano.geoappdemo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucaparlapiano.geoappdemo.model.point.OkResponse
import com.lucaparlapiano.geoappdemo.model.point.Point
import com.lucaparlapiano.geoappdemo.repository.RemoteRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class RemoteViewModel(private val repository: RemoteRepository) : ViewModel() {

    val ResponsePoi: MutableLiveData<Response<OkResponse>> = MutableLiveData()
    val ResponseListPoi: MutableLiveData<Response<List<Point>>> = MutableLiveData()
    val ResponseTrack: MutableLiveData<Response<OkResponse>> = MutableLiveData()

    fun seveRemotePoi(
        userId: String,
        name: String,
        latitude: String,
        longitude: String,
        imgCode: String
    ) {
        viewModelScope.launch {
            val response: Response<OkResponse> = repository.getPoi(
                userId,
                name,
                latitude,
                longitude,
                imgCode
            )
            ResponsePoi.value = response
        }
    }

    fun remoteListPoi(
        userId: String
    ) {
        viewModelScope.launch {
            val response: Response<List<Point>> = repository.getPoiList(userId)
            ResponseListPoi.value = response
        }
    }

    fun sandUsetTrackPosition(
        userId: String,
        pointList: List<Point>
    ) {
        viewModelScope.launch {
            val response: Response<OkResponse> = repository.sendTrackData(
                userId,
                pointList
            )
            ResponseTrack.value = response
        }
    }


}