package com.jbarcelona.jobboardapp.repository

import com.jbarcelona.jobboardapp.network.ApiDataSource
import com.jbarcelona.jobboardapp.network.ApiResource
import com.jbarcelona.jobboardapp.network.ApiService
import com.jbarcelona.jobboardapp.network.model.Job
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository, ApiDataSource() {

    override suspend fun getAllJobs(): ApiResource<List<Job>> {
        return getResult { apiService.getAllJobs() }
    }

    override suspend fun deleteJob(id: Int): ApiResource<Any> {
        return getResult { apiService.deleteJob(id) }
    }
}