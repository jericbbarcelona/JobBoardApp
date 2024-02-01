package com.jbarcelona.jobboardapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jbarcelona.jobboardapp.network.ApiResource
import com.jbarcelona.jobboardapp.network.NetworkResult
import com.jbarcelona.jobboardapp.network.request.InsertJobRequestData
import com.jbarcelona.jobboardapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    val createJobEvent = MutableLiveData<NetworkResult<Any>>()

    fun createJob(noOfOpenings: String, title: String, description: String, position: Int) {
        viewModelScope.launch {
            try {
                val responseData = mainRepository.insertJob(
                    InsertJobRequestData(
                        id = UUID.randomUUID().toString(),
                        noOfOpenings = noOfOpenings.toInt(),
                        title = title,
                        description = description,
                        industry = position
                    )
                )
                if (responseData.status == ApiResource.Status.SUCCESS) {
                    createJobEvent.postValue(NetworkResult.Success(responseData))
                } else {
                    createJobEvent.postValue(NetworkResult.Error(responseData.message.orEmpty()))
                }
            } catch (e: Exception) {
                createJobEvent.postValue(NetworkResult.Error(e.message.toString()))
            }
        }
    }

}