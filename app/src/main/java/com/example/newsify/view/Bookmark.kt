package com.example.newsify.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.newsify.R
import com.example.newsify.Screen.Screen
import com.example.newsify.model.NewsData
import com.example.newsify.viewmodel.NewsViewModel
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookmarkScreen(newsViewModel: NewsViewModel, navController: NavController, ) {
    val id : Long

    Scaffold(

        bottomBar = { BottomNavBar(navController = navController, selectedItem = 2) }
    ) {
        val colors = if (isSystemInDarkTheme()) Color.White else Color.Black
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Add padding around the content
        ) {
            // Title at the top-left corner
            Text(
                text = "Bookmark",
                color = colors,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp, // Set the font size here
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp)// Aligns the text to the start of the Column
            )


            //Spacer(modifier = Modifier.height(16.dp)) // Add some spacing below the title
            val bookmarkedItems = newsViewModel.bookmarkedNews.collectAsState(initial = listOf())
            val bookmarked = bookmarkedItems.value.filter { it.bookmarkStatus == "Marked"  }
            // List of bookmarked items
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 4.dp)
            ) {
                items(bookmarked , key = { item -> item.id }) { news ->

                  bookmarkStyle(news = news, newsViewModel, navController)

                }
            }
        }

    }

}




        @Composable
        fun BottomNavBar(
            navController: NavController,
            selectedItem : Int
        ) {val containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White
            NavigationBar(
                containerColor = containerColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp) // Reduced height for a smaller top bar
                            .padding(horizontal = 8.dp),
            ) {
                NavigationBarItem(

                    selected = selectedItem == 0,
                    onClick = {
                        navController.navigate(Screen.TreandingScreen.route)
                    },

                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            val iconColor =
                                if (selectedItem == 0) colorResource(R.color.primaryblue) else colorResource(
                                    id = R.color.greyscale
                                )
                            val textColor =
                                if (selectedItem == 0) colorResource(R.color.primaryblue) else colorResource(
                                    id = R.color.greyscale
                                )
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_home_24),
                                contentDescription = "Home",
                                tint = iconColor
                            )
                            Text(text = "Home", color = textColor)
                        }
                    },

                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (selectedItem == 0)if (isSystemInDarkTheme()) Color.Black else Color.White else colorResource(
                            R.color.primaryblue
                        )
                    ),
                )

                NavigationBarItem(
                    selected = selectedItem == 1, onClick = {navController.navigate(Screen.ExploreScreen.route) },

                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            val iconColor =
                                if (selectedItem == 1) colorResource(R.color.primaryblue) else colorResource(
                                    id = R.color.greyscale
                                )
                            val textColor =
                                if (selectedItem == 1) colorResource(R.color.primaryblue) else colorResource(
                                    id = R.color.greyscale
                                )
                            val icon = if(selectedItem == 1) painterResource(id = R.drawable.baseline_explore_24) else painterResource(
                                id = R.drawable.outline_explore_24
                            )
                            Icon(
                                painter =icon,
                                contentDescription = "Explore",
                                tint = iconColor
                            )
                            Text(text = "Explore", color = textColor)
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (isSystemInDarkTheme()) Color.Black else Color.White
                    ),

                    )

                NavigationBarItem(
                    selected = selectedItem == 2, onClick = {
                        navController.navigate(Screen.BookmarkScreen.route)
                    },

                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            val iconColor =
                                if (selectedItem == 2) colorResource(R.color.primaryblue) else colorResource(
                                    id = R.color.greyscale
                                )
                            val textColor =
                                if (selectedItem == 2) colorResource(R.color.primaryblue) else colorResource(
                                    id = R.color.greyscale
                                )
                            val icon = if(selectedItem ==2) painterResource(id = R.drawable.baseline_bookmark_24) else painterResource(
                                id = R.drawable.baseline_bookmark_border_24
                            )
                            Icon(
                                painter = icon,
                                contentDescription = "BookMark",
                                tint = iconColor
                            )
                            Text(text = "BookMark", color = textColor)
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (isSystemInDarkTheme()) Color.Black else Color.White
                    )
                )

               /* NavigationBarItem(
                    selected = selectedItem == 3, onClick = { /*TODO*/ },

                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            val iconColor =
                                if (selectedItem == 3) colorResource(R.color.primaryblue) else colorResource(
                                    id = R.color.greyscale
                                )
                            val textColor =
                                if (selectedItem == 3) colorResource(R.color.primaryblue) else colorResource(
                                    id = R.color.greyscale
                                )
                            val icon = if(selectedItem ==2) painterResource(id = R.drawable.baseline_account_circle_24) else painterResource(
                                id = R.drawable.outline_account_circle_24
                            )
                            Icon(
                                painter = icon,
                                contentDescription = "Profile",
                                tint = iconColor
                            )
                            Text(text = "Profile", color = textColor)
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (isSystemInDarkTheme()) Color.Black else Color.White
                    ),

                    )*/

            }
        }
@Composable
fun bookmarkStyle(
    news: NewsData,
    newsViewModel: NewsViewModel, navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                //navController.navigate(Screen.detailScreen.route)
            }
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
            val colors = if (isSystemInDarkTheme()) Color.White else Color.Black
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

            Row ( modifier = Modifier.fillMaxWidth(), // Make the Row take full width
                verticalAlignment = Alignment.CenterVertically){
                PublishDateText(publishedAt = Date(), modifier = Modifier)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                              newsViewModel.deleteById(news)
                    }, // Call the onDeleteClick callback when clicked
                    modifier = Modifier.padding(start = 2.dp).size(22.dp).clickable {

                    } // Add some space to the left of the icon
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,  // Use the delete icon
                        contentDescription = "Delete",
                        tint = Color.Gray, // You can change the tint color to suit your theme

                    )
                }
            }
        }
    }
}
