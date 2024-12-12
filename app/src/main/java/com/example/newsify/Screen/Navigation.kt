package com.example.newsify.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsify.model.NewsData

import com.example.newsify.model.NewsRepository
import com.example.newsify.view.BookmarkScreen
import com.example.newsify.view.DetailScreen
import com.example.newsify.view.ExploreScreen
import com.example.newsify.view.Treanding
import com.example.newsify.view.WebViewFullScreen
import com.example.newsify.viewmodel.NewsViewModel
import com.example.newsify.viewmodel.NewsViewModelFactory
import java.util.Date

@Composable
fun Navigation(
    navController: NavHostController,
    newsRepository: NewsRepository,
) {
    val newsViewModel: NewsViewModel = viewModel(
        factory = NewsViewModelFactory(newsRepository)
    )

    val newsState by newsViewModel._newsState
    val exploreState by newsViewModel.exploreState

    NavHost(
        navController = navController,
        startDestination = Screen.TreandingScreen.route
    ) {
        // Trending Screen Navigation
        composable(route = Screen.TreandingScreen.route ) {
            Treanding(
                newsstate = newsState,
                newsViewModel = newsViewModel,
                navController = navController,
                navigateToDetailScreen = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("newsDetail", it)
                    navController.navigate(Screen.detailScreen.route)
                }
            )
        }

        // Detail Screen Navigation
        // Detail Screen Navigation
        composable(route = Screen.detailScreen.route) {
            val newsData = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<NewsData>("newsDetail")
                ?: NewsData(0L, "", "", "", Date(), "", "", "")
            DetailScreen(news = newsData, navController)
                // Navigate to WebViewFullScreen with NewsData
                navController.currentBackStackEntry?.savedStateHandle?.set("newsDetail", newsData)

        }

        // WebView Full Screen Navigation
        composable(Screen.WebViewFullScreen.route) {
            val newsData = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<NewsData>("newsDetail")
                ?: NewsData(0L, "", "", "", Date(), "", "", "")
            WebViewFullScreen(news = newsData)
        }
        composable(Screen.BookmarkScreen.route){
            BookmarkScreen(newsViewModel = newsViewModel, navController = navController)
        }
        composable(Screen.ExploreScreen.route){
            ExploreScreen(navController = navController, newsViewModel, exploreState, navigateToDetailScreen = {
                navController.currentBackStackEntry?.savedStateHandle?.set("newsDetail", it)
                navController.navigate(Screen.detailScreen.route)
            })
        }



    }

        // Bookmark Detail Screen Navigation (with ID)

    }
