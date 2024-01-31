package com.jbarcelona.jobboardapp.network

object ApiMethod {
    private const val API = "api/"

    object Job {
        private const val JOB = API + "Job/"
        private const val GET_ALL = JOB + "getall"
        private const val INSERT = JOB + "insert"
        private const val UPDATE = JOB + "update"
        private const val DELETE = JOB + "delete"
    }

    object JobApplicant {
        private const val JOB_APPLICANT = API + "JobApplicant/"
        private const val APPLY = JOB_APPLICANT + "applyjob"
        private const val GET_JOBS_APPLIED = JOB_APPLICANT + "getjobsapplied"
    }
}