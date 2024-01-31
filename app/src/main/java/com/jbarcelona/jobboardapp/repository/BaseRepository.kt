package com.jbarcelona.jobboardapp.repository

import com.jbarcelona.jobboardapp.network.ApiResource
import com.jbarcelona.jobboardapp.network.model.Job
import com.jbarcelona.jobboardapp.network.request.ApplyJobRequestData
import com.jbarcelona.jobboardapp.network.request.InsertJobRequestData

interface BaseRepository {

    suspend fun getAllJobs(): ApiResource<List<Job>>
    suspend fun deleteJob(id: String): ApiResource<Any>
    suspend fun insertJob(requestData: InsertJobRequestData): ApiResource<Any>
    suspend fun updateJob(requestData: InsertJobRequestData): ApiResource<Any>
    suspend fun filterJobs(keyword: String, jobIndustryType: Int): ApiResource<List<Job>>
    suspend fun applyJob(requestData: ApplyJobRequestData): ApiResource<Any>
}