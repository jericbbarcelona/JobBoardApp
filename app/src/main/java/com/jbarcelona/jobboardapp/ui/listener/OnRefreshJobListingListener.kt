package com.jbarcelona.jobboardapp.ui.listener

interface OnRefreshJobListingListener {
    fun onUpdateJobListing()
    fun onShowFilteredJobs(keyword: String, industry: Int)
}