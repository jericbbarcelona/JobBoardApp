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
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    val filterJobsEvent = MutableLiveData<NetworkResult<List<Job>>>()

    fun filterJobs(keyword: String, position: Int) {
        viewModelScope.launch {
            try {
                val responseData = mainRepository.filterJobs(keyword, position)
                if (responseData.status == ApiResource.Status.SUCCESS) {
                    filterJobsEvent.postValue(NetworkResult.Success(responseData.data.orEmpty()))
                } else {
                    filterJobsEvent.postValue(NetworkResult.Error(responseData.message.orEmpty()))
                }
            } catch (e: Exception) {
                filterJobsEvent.postValue(NetworkResult.Error(e.message.toString()))
            }
        }
    }
}