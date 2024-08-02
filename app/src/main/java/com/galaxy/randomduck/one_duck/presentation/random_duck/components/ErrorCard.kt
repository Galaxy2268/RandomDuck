package com.galaxy.randomduck.one_duck.presentation.random_duck.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.galaxy.randomduck.R

@Composable
fun ErrorCard(
    action: () -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    elevation: CardElevation = CardDefaults.cardElevation(2.dp),
    colors: CardColors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
){
    Card(
        modifier = modifier,
        elevation = elevation,
        colors = colors
    ) {
        Image(
            painterResource(id = R.drawable.pic404),
            contentDescription = "ERROR",
            modifier = Modifier
                .clip(shape)
                .aspectRatio(1f)
                .clickable { action() },
            contentScale = ContentScale.FillBounds
        )
    }
}