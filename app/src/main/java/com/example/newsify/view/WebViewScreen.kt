package com.example.newsify.view

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.newsify.model.NewsData


@Composable
fun WebViewFullScreen(
    news: NewsData,

) {
    val url = news.url



        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            AndroidView(
                factory = {
                    WebView(it).apply {
                        webChromeClient = WebChromeClient()
                        loadUrl(url)
                    }
                },

                )
        }

    }

