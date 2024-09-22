package com.galaxy.randomduck.one_duck.presentation.random_duck.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode

@Composable
fun InfoScreen(
    navController: NavController
) {

    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Back",
                modifier = Modifier
                    .size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Information",
            style = MaterialTheme.typography.headlineLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Source code",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "https://github.com/Galaxy2268/RandomDuck",
            modifier = Modifier
                .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Galaxy2268/RandomDuck"))
                startActivity(context, intent, null)
            },
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "API",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "https://random-d.uk/api",
            modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://random-d.uk/api"))
                    startActivity(context, intent, null)
                },
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Support",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "Press star on GitHub and rate on Google Play")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val manager = ReviewManagerFactory.create(context)
                val request = manager.requestReviewFlow()
                request.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val reviewInfo = task.result
                        (context as? Activity)?.let { manager.launchReviewFlow(it, reviewInfo) }
                    } else {
                        @ReviewErrorCode val reviewErrorCode = (task.exception as ReviewException).errorCode
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(25),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Rate app in a store",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tips for developer",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier,
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(25),
            ) {
                Text(
                    text = "1€",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Button(
                modifier = Modifier,
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(25),
            ) {
                Text(
                    text = "5€",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Button(
                modifier = Modifier,
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(25),
            ) {
                Text(
                    text = "10€",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Button(
                modifier = Modifier,
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(25),
            ) {
                Text(
                    text = "25€",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Button(
                modifier = Modifier,
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(25),
            ) {
                Text(
                    text = "50€",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

    }
}