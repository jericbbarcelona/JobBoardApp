package com.jbarcelona.jobboardapp.network

import com.jbarcelona.jobboardapp.network.model.Job
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(ApiMethod.Job.GET_ALL)
    suspend fun getAllJobs(): Response<List<Job>>

    @DELETE(ApiMethod.Job.DELETE)
    suspend fun deleteJob(@Path("id") id: Int): Response<Any>
}