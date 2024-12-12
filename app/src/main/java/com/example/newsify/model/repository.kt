package com.example.newsify.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsify.Screen.NewsifyDao
import com.example.newsify.viewmodel.NewsPagingSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NewsRepository(private val newsifyDao: NewsifyDao,){


   // fun getNews() : Flow<List<NewsData>> = newsifyDao.getAllNews()

    //fun getQuery() :Flow<List<NewsData>> = newsifyDao.getAllQuery()

   fun getBookmarkedItem() :Flow<List<NewsData>> = newsifyDao.getBookmarkedItems()


suspend fun addbookmarknews(newsData: List<NewsData>){
    newsifyDao.addbookmarknews(newsData)
}
    fun getbookmarkbyid(newsId :Long) : NewsData? = newsifyDao.getBookmarkById(newsId)

    suspend fun deleteById(newsData: NewsData){
        val id = newsData.id
        newsifyDao.deleteById(id = id)
    }

    suspend fun addNews(newsData: List<NewsData>){
        newsifyDao.addNews(newsData)
    }

    fun getNewsStream(): Flow<PagingData<NewsData>>{
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 5
            ),
            pagingSourceFactory = {
                NewsPagingSource(newsservice)
            }
        ).flow
    }
    val newsservice = retrofit.create(ApiService::class.java)

}

// Define the OkHttpClient with an interceptor for adding the API key to every request
val client = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val originalRequest = chain.request()
        val urlWithApiKey = originalRequest.url.newBuilder()
            .addQueryParameter("apiKey", "18d73f5d6372449fa7425e58fc942d58")  // Add your API key here
            .build()

        val newRequest = originalRequest.newBuilder().url(urlWithApiKey).build()
        chain.proceed(newRequest)
    }
    .build()

// Gson converter setup with the date format
val gson: Gson = GsonBuilder()
    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    .create()

// Create Retrofit instance with the custom OkHttpClient
val retrofit = Retrofit.Builder()
    .baseUrl("https://newsapi.org/")
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(client)
    .build()

// Define the ApiService interface
interface ApiService {
    @GET("v2/top-headlines?country=us")
    suspend fun getnews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse

    @GET("v2/everything")
    suspend fun getQuery(
        @Query("q") text: String   // Accepts a dynamic query string (e.g., "bitcoin")
    ): NewsResponse
}

// Create an instance of the ApiService
val newsservice = retrofit.create(ApiService::class.java)
