package com.galaxy.randomduck.one_duck.presentation.random_duck

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy.randomduck.one_duck.domain.use_case.GetRandomDuck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomDuckViewModel
@Inject constructor(private val getUseCase: GetRandomDuck): ViewModel() {

    private val _state = mutableStateOf(DuckState())
    val state: State<DuckState> = _state



    init {
        getDuck()
    }

    fun getDuck(){
        viewModelScope.launch {
            getUseCase().onRight {
                _state.value = state.value.copy(duck = it, error = null)
            }.onLeft {
                _state.value = state.value.copy(error = it.error.message)
            }
        }
    }

}