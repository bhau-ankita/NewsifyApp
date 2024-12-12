package com.example.newsify.view

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.res.colorResource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.newsify.R
import com.example.newsify.Screen.Screen
import com.example.newsify.model.NewsData
import java.util.Date

@Composable
fun DetailScreen(news: NewsData, navController: NavController) {
    // Determine the current theme (light or dark)
    val colors = MaterialTheme.colorScheme
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val backgroundColor = if (isSystemInDarkTheme()) colors.background else colors.surface

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.padding(8.dp)
            .background(backgroundColor) // Set the background color according to the theme
    ) {

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = news.title ?: "News Detail",
            style = MaterialTheme.typography.titleLarge,
            color = textColor, // Set the text color based on the theme
            modifier = Modifier.padding(8.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(16.dp))

        // News Image
        Image(
            painter = rememberAsyncImagePainter(news.urlToImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth().padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .aspectRatio(16 / 9f) // Maintains a 16:9 aspect ratio
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Optional Details or Content Below
        Row {
            Text(
                text = news.author ?: "Unknown Author",
                modifier = Modifier.padding(8.dp),
                color = textColor, // Use theme's onSurface color for text
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.weight(1f))
            WebViewWithButton(navController = navController)
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = news.description ?: "No content available",
            color = textColor, // Set text color for description
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = news.content ?: "No content available",
            color = textColor, // Set text color for content
            modifier = Modifier.padding(8.dp)
        )

        PublishDateText(publishedAt = news.publishedAt, modifier = Modifier)
    }
}


@Composable
fun WebViewWithButton(
   navController: NavController
) {
    // State to control whether the WebView should be shown
    var showWebView by remember { mutableStateOf(false) }

    if (showWebView ) {
        navController.navigate(Screen.WebViewFullScreen.route)
         {
            // Callback to hide WebView when back button is pressed
            showWebView = false
        }
    } else {
        // Display the button
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            //modifier = Modifier.fillMaxSize()
        ) {
            val colors = if (isSystemInDarkTheme()) Color.White else Color.Black
            val text = if (isSystemInDarkTheme()) Color.Black else Color.White
            Button(onClick = { showWebView = true }, shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors ,  // Background color of the button
                    contentColor = text     // Text color or content color inside the button
                )
                ) {
                Text(text = "Read More >>")
            }
        }
    }
}
