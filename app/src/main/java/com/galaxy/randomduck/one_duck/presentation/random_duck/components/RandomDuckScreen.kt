package com.galaxy.randomduck.one_duck.presentation.random_duck.components

import android.app.DownloadManager
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.imageLoader
import coil.request.ImageRequest
import com.galaxy.randomduck.R
import com.galaxy.randomduck.one_duck.presentation.random_duck.RandomDuckViewModel
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Constants.MAX_OFFSET_X
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Constants.MAX_OFFSET_Y
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Constants.MIN_OFFSET_FOR_ACTION_X
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Constants.MIN_OFFSET_FOR_ACTION_Y
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Constants.MIN_OFFSET_X
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Constants.MIN_OFFSET_Y
import com.galaxy.randomduck.one_duck.presentation.random_duck.components.util.Direction
import com.galaxy.randomduck.one_duck.presentation.util.Screen
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Composable
fun RandomDuckScreen(
    viewModel: RandomDuckViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    var direction by remember { mutableStateOf(Direction.DEFAULT) }
    var actionState by remember { mutableStateOf(Direction.DEFAULT) }
    val downloadManager = context.getSystemService(DownloadManager::class.java)
    var startAnimationJob: Job? = null

    LaunchedEffect(Unit) {
        if (viewModel.state.value.isFirstLaunch) {
            startAnimationJob = coroutineScope.launch {
                delay(2000)
                offsetX.animateTo(-150f)
                offsetX.animateTo(0f)
            }
        }
        viewModel.markAsLaunched()
    }

    LaunchedEffect(key1 = actionState) {
        when (actionState) {
            Direction.START -> {
                viewModel.getPrevDuck()
                actionState = Direction.DEFAULT
            }

            Direction.END -> {
                viewModel.getNextDuck()
                actionState = Direction.DEFAULT
            }

            Direction.TOP -> {
                val request = DownloadManager
                    .Request(state.duck.url.toUri())
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_PICTURES,
                        "duck${state.duck.url.substringAfter("api/")}"
                    )
                downloadManager.enqueue(request)
                actionState = Direction.DEFAULT
            }

            Direction.BOTTOM -> {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, state.duck.url)
                }
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
                actionState = Direction.DEFAULT
            }

            Direction.DEFAULT -> {}
        }
    }

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

    LaunchedEffect(key1 = state.newDucks) {
        state.newDucks.forEach {
            coroutineScope.launch {
                val request = ImageRequest.Builder(context)
                    .data(it.url)
                    .build()
                context.imageLoader.enqueue(request)
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart),
            onClick = {
                navController.navigate(
                    Screen.InfoScreen.toString()
                )
            }
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                modifier = Modifier
                    .size(32.dp)
            )
        }

        if (state.error != null) {
            ErrorCard(
                modifier = Modifier.padding(8.dp),
                action = { viewModel.getNextDuck() }
            )
        } else {
            BackGroundCard(
                id = R.drawable.duck_left,
                modifier = Modifier
                    .padding(8.dp)
                    .offset {
                        IntOffset(
                            x = offsetX.value
                                .roundToInt()
                                .coerceIn(MIN_OFFSET_X..0),
                            y = offsetY.value
                                .roundToInt()
                                .coerceIn(MIN_OFFSET_Y..MAX_OFFSET_Y)
                        )
                    }
            )
            BackGroundCard(
                id = R.drawable.duck_right,
                modifier = Modifier
                    .padding(8.dp)
                    .offset {
                        IntOffset(
                            x = offsetX.value
                                .roundToInt()
                                .coerceIn(0..MAX_OFFSET_X),
                            y = offsetY.value
                                .roundToInt()
                                .coerceIn(MIN_OFFSET_Y..MAX_OFFSET_Y)
                        )
                    }
            )

            BackGroundCard(
                id = R.drawable.send,
                modifier = Modifier
                    .padding(8.dp)
                    .offset {
                        IntOffset(
                            x = offsetX.value
                                .roundToInt()
                                .coerceIn(MIN_OFFSET_X..MAX_OFFSET_X),
                            y = offsetY.value
                                .roundToInt()
                                .coerceIn(0..MAX_OFFSET_Y)
                        )
                    }
            )

            BackGroundCard(
                id = R.drawable.download,
                modifier = Modifier
                    .padding(8.dp)
                    .offset {
                        IntOffset(
                            x = offsetX.value
                                .roundToInt()
                                .coerceIn(MIN_OFFSET_X..MAX_OFFSET_X),
                            y = offsetY.value
                                .roundToInt()
                                .coerceIn(MIN_OFFSET_Y..0)
                        )
                    }
            )

            DuckCard(
                model = state.duck.url,
                modifier = Modifier
                    .pointerInput(Unit){
                        detectTapGestures (
                            onPress = {
                                if(startAnimationJob != null){
                                    startAnimationJob?.cancel()
                                    offsetX.animateTo(0f)
                                }
                            }
                        )
                    }
                    .padding(8.dp)
                    .offset {
                        IntOffset(
                            x = offsetX.value
                                .roundToInt()
                                .coerceIn(MIN_OFFSET_X..MAX_OFFSET_X),
                            y = offsetY.value
                                .roundToInt()
                                .coerceIn(MIN_OFFSET_Y..MAX_OFFSET_Y)
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
                                if (direction == Direction.END && offsetX.value.absoluteValue > MIN_OFFSET_FOR_ACTION_X) {
                                    actionState = Direction.END
                                } else if (direction == Direction.START && offsetX.value > MIN_OFFSET_FOR_ACTION_X) {
                                    actionState = Direction.START
                                }
                            }
                            coroutineScope.launch {
                                offsetX.animateTo(0f)
                                direction = Direction.DEFAULT
                            }
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
                            coroutineScope.launch {
                                if (direction == Direction.TOP && offsetY.value > MIN_OFFSET_FOR_ACTION_Y) {
                                    actionState = Direction.TOP
                                } else if (direction == Direction.BOTTOM && offsetY.value.absoluteValue > MIN_OFFSET_FOR_ACTION_Y) {
                                    actionState = Direction.BOTTOM
                                }
                            }
                            coroutineScope.launch {
                                offsetY.animateTo(0f)
                                direction = Direction.DEFAULT
                            }
                        }
                    )
            )
        }

    }


}


