package com.galaxy.randomduck.one_duck.presentation.random_duck.components

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.galaxy.randomduck.one_duck.presentation.random_duck.RandomDuckViewModel
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Constants.MIN_OFFSET_FOR_ACTION_X
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Constants.RANGE_OFFSET_X
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Constants.RANGE_OFFSET_Y
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Direction
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun RandomDuckScreen(
    viewModel: RandomDuckViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    var direction by remember { mutableStateOf(Direction.DEFAULT) }
    var isActionPerformed by remember { mutableStateOf(false) }



    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            Toast.makeText(
                context,
                state.error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(key1 = state.isLast) {
        if (state.isLast) {
            Toast.makeText(
                context,
                "You got last",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.error != null) {
            ErrorCard(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { viewModel.getNextDuck() }
            )
        } else {
            MockCard(
                text = when (direction) {
                    Direction.START -> "START"
                    Direction.END -> "END"
                    Direction.TOP -> "TOP"
                    Direction.BOTTOM -> "BOTTOM"
                    Direction.DEFAULT -> "DEFAULT"
                }
            )
            DuckCard(
                model = state.duck.url,
                modifier = Modifier
                    .padding(8.dp)
                    .offset {
                        IntOffset(
                            x = offsetX.value
                                .roundToInt()
                                .coerceIn(RANGE_OFFSET_X),
                            y = offsetY.value
                                .roundToInt()
                                .coerceIn(RANGE_OFFSET_Y)
                        )
                    }
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
                            coroutineScope.launch {
                                direction =
                                    if (offsetX.value < 0) Direction.END else Direction.START
                                offsetX.snapTo(
                                    offsetX.value + delta
                                )
                            }
                        },
                        onDragStopped = {
                            coroutineScope.launch {
                                if (!isActionPerformed) {
                                    coroutineScope.launch {
                                        if (direction == Direction.END && offsetX.value < MIN_OFFSET_FOR_ACTION_X) {
                                            viewModel.getNextDuck()
                                            isActionPerformed = true
                                        } else if (direction == Direction.START && offsetX.value > MIN_OFFSET_FOR_ACTION_X) {
                                            viewModel.getPrevDuck()
                                            isActionPerformed = true
                                        }
                                    }
                                }
                            }
                            offsetX.animateTo(0f)
                            direction = Direction.DEFAULT
                            isActionPerformed = false
                        }
                    )
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            coroutineScope.launch {
                                direction =
                                    if (offsetY.value < 0) Direction.BOTTOM else Direction.TOP
                                offsetY.snapTo(
                                    offsetY.value + delta
                                )
                            }
                        },
                        onDragStopped = {
                            offsetY.animateTo(0f)
                            direction = Direction.DEFAULT
                        }
                    )
            )
        }

    }


}


