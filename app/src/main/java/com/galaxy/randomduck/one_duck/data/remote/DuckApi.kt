package com.galaxy.randomduck.one_duck.data.remote

import com.galaxy.randomduck.one_duck.domain.model.Duck
import retrofit2.http.GET

interface DuckApi {
    @GET("random")
    suspend fun getRandomDuck(): Duck
}