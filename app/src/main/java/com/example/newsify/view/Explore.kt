package com.example.newsify.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.newsify.R
import com.example.newsify.Screen.Screen
import com.example.newsify.model.NewsData
import com.example.newsify.viewmodel.NewsViewModel
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExploreScreen( navController: NavController, newsViewModel: NewsViewModel, exploreState: NewsViewModel.ExploreState, navigateToDetailScreen: (NewsData) -> Unit,) {

    var isLoading by remember { mutableStateOf(false) }
    val searchLoading = newsViewModel.searchloading.value
    Scaffold(

     bottomBar = { BottomNavBar(navController =navController , selectedItem = 1)}
    ) { val colors = if (isSystemInDarkTheme()) Color.White else Color.Black
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) , // Add padding around the content
        ) {
            // Title at the top-left corner
            Text(
                text = "Explore",
                color = colors,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp, // Set the font size here
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp)
            )
          Search(newsViewModel, isLoading, onLoadingChange = { isLoading = it })


            when {
                searchLoading -> {
                    CircularProgressIndicator(modifier = Modifier
                        .padding(top = 30.dp)
                        .align(Alignment.CenterHorizontally))
                }

                exploreState.error != null -> {
                    Text(text = "Error Occurred", color = Color.Red)
                }
                else -> {
                    SearchCoulumn(newsresponse = exploreState.list, navigateToDetailScreen = navigateToDetailScreen )
                }
            }
        }
    }
}

@Composable
fun SearchCoulumn(newsresponse :List<NewsData>, navigateToDetailScreen: (NewsData) -> Unit,){
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 4.dp)
    ){
        items(newsresponse){
                news -> ExploreStyle(news = news, navigateToDetailScreen)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(newsViewModel: NewsViewModel, isLoading: Boolean,
           onLoadingChange: (Boolean) -> Unit,

           ){
    var text by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

    Column (modifier =
    Modifier.fillMaxWidth(1f)
    ){
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            val colors = if (isSystemInDarkTheme()) Color.White else Color.Black
            OutlinedTextField(value = text, onValueChange ={
                text = it
            },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Search")},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colors,
                    unfocusedBorderColor = colors,
                    focusedLabelColor = colors,
                    cursorColor = colors,
                    focusedTextColor = colors,
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        if (text.isNotEmpty()) {
                            onLoadingChange(true) // Show loading indicator
                            newsViewModel.fetchQuery(text) // Fetch data
                            onLoadingChange(false)// Hide loading indicator when done
                          focusManager.clearFocus()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription ="Search", tint = colors )
                    }
                }
            )

        }
    }
}



@Composable
fun ExploreStyle(
    news: NewsData,
    navigateToDetailScreen: (NewsData) -> Unit,
) {
    val colors = if (isSystemInDarkTheme()) Color.White else Color.Black
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToDetailScreen(news) }
            .padding(6.dp), // Add padding around the Row
        verticalAlignment = Alignment.CenterVertically // Align items vertically at the center
    ) {
        // Image Box with rounded corners
        Box(
            modifier = Modifier
                //.padding(4.dp) // Add padding around the Box
                .size(100.dp) // Set the height and width of the Box
                .clip(RoundedCornerShape(10.dp)) // Apply rounded corners with 16dp radius
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = news.urlToImage,
                    // Placeholder and error handling (optional)
                    // placeholder = painterResource(R.drawable.placeholder),
                    // error = painterResource(R.drawable.error_placeholder)
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop, // Crop the image to fit the Box
                modifier = Modifier.matchParentSize() // Ensures the image fills the Box
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Add space between the image and the column

        // Column with text content
        Column(
            modifier = Modifier.fillMaxWidth() // Make the Column fill the available width
        ) {
            Text(
                text = news.author ?: "Unkown",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = colors,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = news.title,
                // modifier = Modifier.padding(4.dp), // Add padding around the title
                color = colors, // Title color
                maxLines = 2, // Limit the number of lines for the title
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis
            )
            PublishDateText(publishedAt = Date(), modifier = Modifier)
        }
    }
}

