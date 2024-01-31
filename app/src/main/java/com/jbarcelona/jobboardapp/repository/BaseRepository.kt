package com.jbarcelona.jobboardapp.repository

import com.jbarcelona.jobboardapp.network.ApiResource
import com.jbarcelona.jobboardapp.network.model.Job

interface BaseRepository {

    suspend fun getAllJobs(): ApiResource<List<Job>>
    suspend fun deleteJob(id: Int): ApiResource<Any>
}