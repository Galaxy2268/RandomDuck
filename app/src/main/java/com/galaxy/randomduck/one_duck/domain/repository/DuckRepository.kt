package com.galaxy.randomduck.one_duck.domain.repository

import arrow.core.Either
import com.galaxy.randomduck.one_duck.domain.model.Duck
import com.galaxy.randomduck.one_duck.domain.model.NetworkError

interface DuckRepository {

    suspend fun getRandomDuck(): Either<NetworkError, List<Duck>>

}