package com.jbarcelona.jobboardapp.network.request

import com.squareup.moshi.Json

data class ApplyJobRequestData(
    @Json(name = "id")
    var id: String? = null,
    @Json(name = "fullName")
    var fullName: String? = null,
    @Json(name = "emailAddress")
    var emailAddress: String? = null
)