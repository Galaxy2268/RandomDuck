package com.galaxy.randomduck.one_duck.presentation.random_duck.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        DuckCard(
            onClick = { viewModel.getDuck() },
            duck = state.duck,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }


}