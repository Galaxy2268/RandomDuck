package com.galaxy.randomduck.one_duck.data.repository

import arrow.core.Either
import com.galaxy.randomduck.one_duck.data.mapper.toNetworkError
import com.galaxy.randomduck.one_duck.data.remote.DuckApi
import com.galaxy.randomduck.one_duck.domain.model.Duck
import com.galaxy.randomduck.one_duck.domain.model.NetworkError
import com.galaxy.randomduck.one_duck.domain.repository.DuckRepository

class DuckRepositoryImpl(private val api: DuckApi): DuckRepository {
    override suspend fun getRandomDuck(): Either<NetworkError, Duck> {
        return Either.catch {
            api.getRandomDuck()
        }.mapLeft { it.toNetworkError() }
    }


}