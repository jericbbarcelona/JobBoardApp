package com.jbarcelona.jobboardapp.network

sealed class NetworkResult<out T> {
    data class Error(val message: String) : NetworkResult<Nothing>()
    data class Success<T>(val result: T) : NetworkResult<T>()
}