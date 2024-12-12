package com.example.newsify.view

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.example.newsify.R
import com.example.newsify.Screen.Screen
import com.example.newsify.model.NewsData
import com.example.newsify.viewmodel.NewsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Treanding(
    modifier: Modifier = Modifier,
     newsstate:NewsViewModel.NewsState,
    navigateToDetailScreen :(NewsData) -> Unit,
    newsViewModel: NewsViewModel,
    navController : NavController,

){
    //val newsList by newsViewModel.getAllNewa.collectAsState(initial = listOf())



    Box(modifier = Modifier
        //.fillMaxSize()
        // .padding(8.dp)
        .background(color = Color.White)) {

      Scaffold(

          bottomBar = { BottomNavBar(navController, selectedItem = 0)}
      ) {innerPadding ->

      Box(modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
          .padding(8.dp)) {

       /*   when{
              newsstate.loading ->{
                  CircularProgressIndicator(modifier.align(Alignment.Center))
              }

              newsstate.error != null ->{
                  Text("ERROR OCCURRED")
              }
              else ->{
                  TreandinScreen(
                     // newsresponse = newsstate.list,
                      navigateToDetailScreen = navigateToDetailScreen,
                      newsviewModel = newsViewModel,
                      navController = navController
                  )
              }
          }*/
          TreandinScreen(navigateToDetailScreen = navigateToDetailScreen, newsviewModel = newsViewModel, navController = navController)
          }
      }


    }

}


@Composable
fun TreandinScreen(
  //  newsresponse: List<NewsData>,
    navigateToDetailScreen: (NewsData) -> Unit,
    newsviewModel: NewsViewModel,
    navController: NavController
    ) {
    val news :NewsData
    val pagingData = newsviewModel.pagingData.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Add padding around the content
    ) {
        val colors = if (isSystemInDarkTheme()) Color.White else Color.Black
        // Title at the top-left corner
        Text(
            text = "Trending",
            color = colors,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp, // Set the font size here
            modifier = Modifier.align(Alignment.Start) // Aligns the text to the start of the Column
        )

        /*  LazyColumn(
          modifier = Modifier
            .fillMaxSize()
          .padding(bottom = 4.dp)
        ) {
          items(newsresponse) { news ->
            NewsItem(news = news, navigateToDetailScreen, newsviewModel, navController)
        }
        }*/
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 4.dp)
        ) {
            if(pagingData.loadState.refresh is LoadState.Loading){
                item{
                    Box (modifier = Modifier.fillParentMaxSize()){
                      CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                }
            }
            if(pagingData.loadState.refresh is LoadState.NotLoading){
                items(pagingData) {
                    it?.let {
                        Log.d("Newsify", "Fetched item: $it")
                        NewsItem(
                            news = it,
                            navigateToDetailScreen = navigateToDetailScreen,
                            newsviewModel = newsviewModel,
                            navController = navController,
                        )
                    }
                }
            }
            if(pagingData.loadState.refresh is LoadState.Error){
                item{
                    Box (modifier = Modifier.fillParentMaxSize()){
                      Text(text = "Error Occur", modifier = Modifier.clickable {
                          pagingData.refresh()
                      })
                    }
                }
            }


            if (pagingData.loadState.append is LoadState.Loading){
                item {
                    Box (modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)){
                      CircularProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))
                    }
                }
            }
            if(pagingData.loadState.append is LoadState.Error){
                item {
                    Box (modifier = Modifier.fillMaxWidth()){
                        Text(text = "Error occur ")
                    }
                }
            }

            if(pagingData.loadState.prepend is LoadState.Loading){
                item{
                    Box (modifier = Modifier.fillParentMaxSize()){
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))
                    }
                }
            }
            if(pagingData.loadState.prepend is LoadState.Error){
                item {
                    Box (modifier = Modifier.fillMaxWidth()){
                        Text(text = "Page is not loading ")
                    }
                }

            }
        }
    }
    }
@Composable
fun NewsItem(news :NewsData,
             navigateToDetailScreen: (NewsData) -> Unit,
             newsviewModel: NewsViewModel,
             navController: NavController,

){

    Column(modifier = Modifier
        .padding(2.dp)
        .fillMaxSize()
        .clickable {
            navigateToDetailScreen(news)
        }
        ) {
        val colors = if (isSystemInDarkTheme()) Color.White else Color.Black
        if (!news.urlToImage.isNullOrEmpty()) {
            if (!news.title.isNullOrEmpty() && !news.title.contains("remove", ignoreCase = true)) {
                Text(
                    text = news.title,
                    modifier = Modifier.padding(2.dp),
                    color = colors, // Assuming colors is defined
                    maxLines = 2,
                    style = MaterialTheme.typography.titleLarge
                )
            }


            // Image
            if (!news.urlToImage.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(180.dp) // Only applies when the image exists
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = news.urlToImage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize() // Ensures the image fills the Box
                    )
                }
            }

            // Row containing Author, Date, and Bookmark
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
               // .padding(vertical = 4.dp), // Add padding to the row
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically // Align items vertically in the center
            ) {
                // Author Text
                if (!news.author.isNullOrEmpty()) {
                    Text(
                        text = news.author ?: "Unknown Author",
                       modifier = Modifier.padding(2.dp),
                        color = colorResource(id = R.color.greyscale),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp
                    )
                }

                // Publish Date

                PublishDateText(
                    publishedAt = news.publishedAt,
                   // modifier = Modifier.padding(horizontal = 2.dp) // Add spacing between items
                )

               Spacer(modifier = Modifier.weight(1F))
                // Bookmark Icon
                Bookmark(
                    news = news,
                    newsViewModel = newsviewModel,
                    navController = navController
                )
            }



            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    onBackNavClicked: () -> Unit = {}
) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(24.dp),
                color = Color.Black

            )

        },
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp) // Reduced height for a smaller top bar
            .padding(horizontal = 8.dp), // Adjust padding as needed
        //backgroundColor = colorResource(R.color.primaryblue), // Set your desired background color
        //elevation = 4.dp,

        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack, contentDescription = "back",
                modifier = Modifier
                    .clickable { onBackNavClicked() },
                tint = colorResource(id = R.color.greyscale)

            )
        },

        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = colorResource(id = R.color.greyscale)

                )
            }
        },
    )
}
@Composable
fun PublishDateText(publishedAt: Date, modifier: Modifier = Modifier) {
    val formattedDate = try {
        val currentDate = Calendar.getInstance().time
        val diffInMillis = currentDate.time - publishedAt.time
        val diffInDays = (diffInMillis / (1000 * 60 * 60 * 24)).toInt() // Convert to days

        when {
            diffInDays == 0 -> "Today"
            diffInDays == 1 -> "Yesterday"
            diffInDays in 2..6 -> "$diffInDays days ago"
            diffInDays >= 7 -> "1 week ago"
            else -> {
                val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                outputFormat.format(publishedAt)
            }
        }
    } catch (e: Exception) {
        "Unknown Date" // Fallback in case of error
    }

    Text(
        text = formattedDate,
        modifier = modifier.padding(4.dp),
        color = colorResource(id = R.color.greyscale),
        fontSize = 12.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}


@Composable
fun Bookmark(
    news: NewsData,
    newsViewModel: NewsViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val bookmarkedNewsList by newsViewModel.bookmarkedNews.collectAsState()

    // Check if the current news item is already bookmarked
    val isBookmarked = bookmarkedNewsList.any { it.id == news.id }
    var isToggling by remember { mutableStateOf(false) }


        IconButton(
            onClick = {
                if (!isToggling) {
                    if (!isBookmarked) { // Prevent bookmarking again if already bookmarked
                        isToggling = true

                        // Create a copy of the news item with updated bookmark status
                        val updatedNews = news.copy(
                            bookmarked = true,
                            bookmarkStatus = "Marked",
                            content = news.content ?: ""  // Handle null case for content
                        )

                        // Add the item to the Room database
                        newsViewModel.addBookmarkNews(listOf(updatedNews))

                        // Show a toast
                        Toast.makeText(context, "Added to bookmarks", Toast.LENGTH_SHORT).show()

                        isToggling = false
                    } else {
                        // Show a message if already bookmarked
                        Toast.makeText(context, "Already bookmarked", Toast.LENGTH_SHORT).show()
                    }
                }
            },
                //.padding(4.dp)
        ) {
            val colors = if (isSystemInDarkTheme()) Color.White else Color.Black
            Icon(
                painter = painterResource(
                    id = if (isBookmarked) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24
                ),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = colors
            )
        }
    }
