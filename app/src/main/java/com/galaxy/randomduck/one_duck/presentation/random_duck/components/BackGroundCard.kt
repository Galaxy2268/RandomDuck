package com.galaxy.randomduck.one_duck.presentation.random_duck.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BackGroundCard(
    id: Int,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    elevation: CardElevation = CardDefaults.cardElevation(2.dp),
    colors: CardColors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
){
    ElevatedCard(
        modifier = modifier,
        shape = shape,
        elevation = elevation,
        colors = colors
    ) {
        Image(
            painterResource(id),
            contentDescription = "BackGroundCard",
            modifier = Modifier
                .aspectRatio(0.9f),
            contentScale = ContentScale.FillBounds
        )
    }
}