package com.jbarcelona.jobboardapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jbarcelona.jobboardapp.network.ApiResource
import com.jbarcelona.jobboardapp.network.NetworkResult
import com.jbarcelona.jobboardapp.network.request.InsertJobRequestData
import com.jbarcelona.jobboardapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateJobViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    val updateJobEvent = MutableLiveData<NetworkResult<Any>>()

    fun updateJob(jobId: String, noOfOpenings: String, title: String, description: String, position: Int) {
        viewModelScope.launch {
            try {
                val responseData = mainRepository.updateJob(
                    InsertJobRequestData(
                        id = jobId,
                        noOfOpenings = noOfOpenings.toInt(),
                        title = title,
                        description = description,
                        industry = position
                    )
                )
                if (responseData.status == ApiResource.Status.SUCCESS) {
                    updateJobEvent.postValue(NetworkResult.Success(responseData))
                } else {
                    updateJobEvent.postValue(NetworkResult.Error(responseData.message.orEmpty()))
                }
            } catch (e: Exception) {
                updateJobEvent.postValue(NetworkResult.Error(e.message.toString()))
            }
        }
    }

}