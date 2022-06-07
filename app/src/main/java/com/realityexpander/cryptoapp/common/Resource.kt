package com.realityexpander.cryptoapp.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null, val errorMessage: String = "", val errorCode: Int? = null): Resource<T>(data, message)
}

