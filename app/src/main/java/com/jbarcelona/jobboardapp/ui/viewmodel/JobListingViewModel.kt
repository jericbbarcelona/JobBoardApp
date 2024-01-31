package com.jbarcelona.jobboardapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jbarcelona.jobboardapp.network.model.Job
import com.jbarcelona.jobboardapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobListingViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    val populateJobListEvent = MutableLiveData<JobResult>()

    fun getAllJobs() {
        viewModelScope.launch {
            try {
                val responseData = mainRepository.getAllJobs()
                populateJobListEvent.postValue(JobResult.Success(responseData.data))
            } catch (e: Exception) {
                populateJobListEvent.postValue(JobResult.Error(e.message.toString()))
            }
        }
    }

    sealed class JobResult {
        class Success(val responseData: List<Job>?) : JobResult()
        class Error(val message: String) : JobResult()
    }
}