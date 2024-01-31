package com.jbarcelona.jobboardapp.network.request

import com.squareup.moshi.Json

data class InsertJobRequestData(
    @Json(name = "id")
    var id: String? = null,
    @Json(name = "noOfOpenings")
    var noOfOpenings: Int? = null,
    @Json(name = "title")
    var title: String? = null,
    @Json(name = "description")
    var description: String? = null,
    @Json(name = "industry")
    var industry: Int? = null
)