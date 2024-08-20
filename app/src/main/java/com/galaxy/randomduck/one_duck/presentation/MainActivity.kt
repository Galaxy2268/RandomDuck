package com.galaxy.randomduck.one_duck.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.InfoScreen
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.RandomDuckScreen
import com.galaxy.randomduck.one_duck.presentation.util.Screen
import com.galaxy.randomduck.ui.theme.RandomDuckTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomDuckTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RandomDuckScreen.toString()
                    ) {
                        composable(route = Screen.RandomDuckScreen.toString()) {
                            RandomDuckScreen(
                                navController = navController,
                            )
                        }

                        composable(route = Screen.InfoScreen.toString()){
                            InfoScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

