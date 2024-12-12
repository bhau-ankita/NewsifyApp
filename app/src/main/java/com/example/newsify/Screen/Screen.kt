package com.example.newsify.Screen

 sealed class Screen(val route : String) {
     object  TreandingScreen: Screen("treandingcreen")
     object detailScreen : Screen("detailScreen")
     object  BookmarkScreen : Screen("bookmarkscreen")
     object ExploreScreen : Screen("exploreScreen")
     object WebViewFullScreen : Screen("WebViewFullAcreen")
}