package com.jbarcelona.jobboardapp.repository

import com.jbarcelona.jobboardapp.network.ApiDataSource
import com.jbarcelona.jobboardapp.network.ApiResource
import com.jbarcelona.jobboardapp.network.ApiService
import com.jbarcelona.jobboardapp.network.model.Job
import com.jbarcelona.jobboardapp.network.request.ApplyJobRequestData
import com.jbarcelona.jobboardapp.network.request.InsertJobRequestData
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository, ApiDataSource() {

    override suspend fun getAllJobs(): ApiResource<List<Job>> {
        return getResult { apiService.getAllJobs() }
    }

    override suspend fun deleteJob(id: String): ApiResource<Any> {
        return getResult { apiService.deleteJob(id) }
    }

    override suspend fun insertJob(requestData: InsertJobRequestData): ApiResource<Any> {
        return getResult { apiService.insertJob(requestData) }
    }

    override suspend fun updateJob(requestData: InsertJobRequestData): ApiResource<Any> {
        return getResult { apiService.updateJob(requestData) }
    }

    override suspend fun filterJobs(keyword: String, jobIndustryType: Int): ApiResource<List<Job>> {
        return getResult { apiService.filterJobs(keyword, jobIndustryType) }
    }

    override suspend fun applyJob(jobId: String, requestData: ApplyJobRequestData): ApiResource<Any> {
        return getResult { apiService.applyJob(jobId, requestData) }
    }
}