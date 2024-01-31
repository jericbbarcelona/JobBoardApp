package com.jbarcelona.jobboardapp.network


import retrofit2.Response

abstract class ApiDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ApiResource.success(body)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): ApiResource<T> {
        return ApiResource.error("Oops, something's not working. Please try again.\n\n$message")
    }
}