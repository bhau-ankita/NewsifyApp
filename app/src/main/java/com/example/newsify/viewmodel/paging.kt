package com.example.newsify.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsify.model.ApiService
import com.example.newsify.model.NewsData
import com.example.newsify.model.NewsRepository
import com.example.newsify.model.NewsResponse
import com.example.newsify.model.newsservice
import kotlinx.coroutines.delay
import okio.IOException
import retrofit2.HttpException


class NewsPagingSource (private  val apiService: ApiService) : PagingSource<Int , NewsData>(){
    override fun getRefreshKey(state: PagingState<Int, NewsData>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(
            1
            )?:state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

   // @SuppressLint("SuspiciousIndentation")
   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsData> {
       val position = params.key ?: 1 // Default to page 1 if no key is provided
       return try {
           delay(1000L)
           // Get data from the API
           val remoteData = newsservice.getnews(position, params.loadSize)
           Log.d("News", "No Data: ${remoteData}")
           Log.d("Newsify", "Articles count: ${remoteData.articles.size}")

           // Total items loaded so far
           val itemsLoaded = position * params.loadSize

           // Determine if more data can be loaded
           val nextKey = if (itemsLoaded < remoteData.totalResults) {
               position + 1 // Load the next page
           } else {
               null // Stop pagination
           }

           // Return the paginated data
           LoadResult.Page(
               data = remoteData.articles,  // List of articles for the current page
               prevKey = if (position == 1) null else position - 1, // Previous page key
               nextKey = nextKey // Next page key
           )
       } catch (e: IOException) {
           Log.e("News", "IOException: ${e.message}")
           LoadResult.Error(e) // Handle network errors
       } catch (e: HttpException) {
           Log.e("News", "HttpException: ${e.message}")
           LoadResult.Error(e) // Handle API errors
       }
   }

}