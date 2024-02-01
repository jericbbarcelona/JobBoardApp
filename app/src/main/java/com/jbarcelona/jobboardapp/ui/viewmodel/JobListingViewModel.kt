package com.jbarcelona.jobboardapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jbarcelona.jobboardapp.network.ApiResource
import com.jbarcelona.jobboardapp.network.NetworkResult
import com.jbarcelona.jobboardapp.network.model.Job
import com.jbarcelona.jobboardapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobListingViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    val populateJobListEvent = MutableLiveData<NetworkResult<List<Job>>>()
    val noDataEvent = MutableLiveData<Boolean>()
    val deleteJobEvent = MutableLiveData<NetworkResult<Any>>()

    fun getAllJobs() {
        viewModelScope.launch {
            try {
                val responseData = mainRepository.getAllJobs()
                if (responseData.status == ApiResource.Status.SUCCESS) {
                    populateJobListEvent.postValue(NetworkResult.Success(responseData.data.orEmpty()))
                } else {
                    populateJobListEvent.postValue(NetworkResult.Error(responseData.message.orEmpty()))
                    noDataEvent.value = true
                }
            } catch (e: Exception) {
                populateJobListEvent.postValue(NetworkResult.Error(e.message.toString()))
                noDataEvent.value = true
            }
        }
    }

    fun deleteJob(job: Job) {
        viewModelScope.launch {
            try {
                val responseData = mainRepository.deleteJob(job.id.orEmpty())
                deleteJobEvent.postValue(NetworkResult.Success(responseData))
            } catch (e: Exception) {
                deleteJobEvent.postValue(NetworkResult.Error(e.message.toString()))
            }
        }
    }
}