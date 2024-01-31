package com.jbarcelona.jobboardapp.network

import com.jbarcelona.jobboardapp.network.model.Job
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(ApiMethod.Job.GET_ALL)
    suspend fun getAllJobs(): Response<List<Job>>
}