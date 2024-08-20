package com.galaxy.randomduck.one_duck.presentation.util

import kotlinx.serialization.Serializable

sealed class Screen{
    @Serializable
    data object RandomDuckScreen: Screen()
    @Serializable
    data object InfoScreen: Screen()
}