package com.example.newsify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsify.Screen.Navigation
import com.example.newsify.Screen.NewsifyDao
import com.example.newsify.model.NewsRepository
import com.example.newsify.ui.theme.NewsifyTheme

import com.example.newsify.view.Treanding
import com.example.newsify.viewmodel.Graph.newsRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()

            NewsifyTheme {
                // Pass the initialized newsDataRepo to Navigation
                Navigation(
                    navController = navController,
                    newsRepository = newsRepository, // Use initialized newsDataRepo // If this is required as a separate argument
                )
            }
        }
    }
}
