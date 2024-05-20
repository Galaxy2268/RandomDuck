package com.galaxy.randomduck.one_duck.presentation.random_duck.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.galaxy.randomduck.one_duck.presentation.random_duck.RandomDuckViewModel

@Composable
fun RandomDuckScreen(
    viewModel: RandomDuckViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if(state.error != null){
            ErrorCard(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { viewModel.getDuck() }
            )
        }else{
            DuckCard(
                duck = state.duck,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { viewModel.getDuck() }
            )
        }

    }


}