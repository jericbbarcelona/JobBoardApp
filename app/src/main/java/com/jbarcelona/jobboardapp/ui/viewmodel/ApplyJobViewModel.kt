package com.jbarcelona.jobboardapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jbarcelona.jobboardapp.network.NetworkResult
import com.jbarcelona.jobboardapp.network.request.ApplyJobRequestData
import com.jbarcelona.jobboardapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApplyJobViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    val applyJobEvent = MutableLiveData<NetworkResult<Any>>()

    fun applyJob(jobId: String, firstName: String, lastName: String, emailAddress: String) {
        viewModelScope.launch {
            try {
                val fullName = "$firstName $lastName"
                val responseData = mainRepository.applyJob(
                    ApplyJobRequestData(
                        id = jobId,
                        fullName = fullName,
                        emailAddress = emailAddress
                    )
                )
                applyJobEvent.postValue(NetworkResult.Success(responseData))
            } catch (e: Exception) {
                applyJobEvent.postValue(NetworkResult.Error(e.message.toString()))
            }
        }
    }
}