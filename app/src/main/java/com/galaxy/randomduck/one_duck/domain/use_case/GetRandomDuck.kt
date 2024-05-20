package com.galaxy.randomduck.one_duck.domain.use_case

import arrow.core.Either
import com.galaxy.randomduck.one_duck.domain.model.Duck
import com.galaxy.randomduck.one_duck.domain.model.NetworkError
import com.galaxy.randomduck.one_duck.domain.repository.DuckRepository

class GetRandomDuck(private val repository: DuckRepository) {
    suspend operator fun invoke(): Either<NetworkError, Duck>{
        return repository.getRandomDuck()
    }
}