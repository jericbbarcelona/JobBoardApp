package com.jbarcelona.jobboardapp.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job(
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
): Parcelable