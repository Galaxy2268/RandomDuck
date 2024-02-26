package com.galaxy.randomduck.one_duck.presentation.random_duck

import com.galaxy.randomduck.one_duck.domain.model.Duck

data class DuckState(
    val duck: Duck = Duck(""),
    val error: String? = null
)
