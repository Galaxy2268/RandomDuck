package com.galaxy.randomduck.one_duck.data.mapper

import com.galaxy.randomduck.one_duck.domain.model.ApiError
import com.galaxy.randomduck.one_duck.domain.model.NetworkError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toNetworkError(): NetworkError{
    val error = when(this){
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.NetworkError
        else -> ApiError.UnknownError
    }
    return NetworkError(error = error, t = this)
}