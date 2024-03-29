package com.jbarcelona.jobboardapp.ui.viewmodel

import com.jbarcelona.jobboardapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FilterJobViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

}