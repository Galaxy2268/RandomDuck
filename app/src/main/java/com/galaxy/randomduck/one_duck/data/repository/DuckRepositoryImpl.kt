package com.galaxy.randomduck.one_duck.data.repository

import arrow.core.Either
import com.galaxy.randomduck.one_duck.data.mapper.toNetworkError
import com.galaxy.randomduck.one_duck.data.remote.DuckApi
import com.galaxy.randomduck.one_duck.domain.model.Duck
import com.galaxy.randomduck.one_duck.domain.model.NetworkError
import com.galaxy.randomduck.one_duck.domain.repository.DuckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class DuckRepositoryImpl(private val api: DuckApi): DuckRepository {
    override suspend fun getRandomDuck(): Either<NetworkError, List<Duck>> {
        return Either.catch {
            coroutineScope {
                val deferredDucks = List(10) {
                    async(Dispatchers.IO) {
                        api.getRandomDuck()
                    }
                }
                deferredDucks.awaitAll()
            }
        }.mapLeft { it.toNetworkError() }
    }

}