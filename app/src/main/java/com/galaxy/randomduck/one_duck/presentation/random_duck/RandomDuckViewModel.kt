package com.galaxy.randomduck.one_duck.presentation.random_duck

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy.randomduck.one_duck.domain.model.Duck
import com.galaxy.randomduck.one_duck.domain.use_case.GetRandomDuck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomDuckViewModel
@Inject constructor(private val getUseCase: GetRandomDuck) : ViewModel() {

    private val _state = mutableStateOf(DuckState())
    val state: State<DuckState> = _state

    private val duckList: MutableList<Duck> = mutableListOf()
    private var counter = -1



    init {
        getInitDucks()
    }

    fun getNextDuck() {

        if(duckList.isEmpty() || _state.value.error != null){
            getInitDucks()
            return
        }

        if (duckList.size - 5 == counter) {
            getDucks()
            return
        }
        counter++
        _state.value = state.value.copy(duck = duckList[counter], error = null, isLast = false)
    }


    private fun getDucks() {
        viewModelScope.launch {
            counter++
            _state.value = state.value.copy(duck = duckList[counter])
            getUseCase().onRight {
                duckList.addAll(it)
                _state.value = state.value.copy(error = null, isLast = false, newDucks = it)
            }.onLeft {
                _state.value = state.value.copy(error = it.error.message)
            }
        }
    }

    private fun getInitDucks(){
        viewModelScope.launch {
            getUseCase().onRight {
                counter++
                duckList.addAll(it)
                _state.value = state.value.copy(duck = duckList[counter], error = null, isLast = false, newDucks = it)
            }.onLeft {
                _state.value = state.value.copy(error = it.error.message)
            }
        }
    }


    fun getPrevDuck(){
        if(counter <= 0){
            _state.value = state.value.copy(isLast = true)
            return
        }
        counter--
        _state.value = state.value.copy(duck = duckList[counter])

    }

}