package com.jbarcelona.jobboardapp.network

object ApiMethod {
    private const val API = "api/"

    object Job {
        private const val JOB = API + "Job/"
        const val GET_ALL = JOB + "getall"
        const val INSERT = JOB + "insert"
        const val UPDATE = JOB + "update"
        const val DELETE = JOB + "delete{id}"
    }

    object JobApplicant {
        private const val JOB_APPLICANT = API + "JobApplicant/"
        const val APPLY = JOB_APPLICANT + "applyjob"
        const val GET_JOBS_APPLIED = JOB_APPLICANT + "getjobsapplied"
    }
}