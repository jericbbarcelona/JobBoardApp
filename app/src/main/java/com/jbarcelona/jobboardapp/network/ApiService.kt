package com.jbarcelona.jobboardapp.network

import com.jbarcelona.jobboardapp.network.model.Job
import com.jbarcelona.jobboardapp.network.request.ApplyJobRequestData
import com.jbarcelona.jobboardapp.network.request.InsertJobRequestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    @GET(ApiMethod.Job.GET_ALL)
    suspend fun getAllJobs(): Response<List<Job>>

    @DELETE(ApiMethod.Job.DELETE)
    suspend fun deleteJob(@Query("id") id: String): Response<Any>

    @POST(ApiMethod.Job.INSERT)
    suspend fun insertJob(@Body requestData: InsertJobRequestData): Response<Any>

    @PUT(ApiMethod.Job.UPDATE)
    suspend fun updateJob(@Body requestData: InsertJobRequestData): Response<Any>

    @GET(ApiMethod.Job.FILTER)
    suspend fun filterJobs(
        @Query("keyword") keyword: String,
        @Query("jobIndustryType") jobIndustryType: Int
    ): Response<List<Job>>

    @POST(ApiMethod.JobApplicant.APPLY_JOB)
    suspend fun applyJob(@Body requestData: ApplyJobRequestData): Response<Any>

}