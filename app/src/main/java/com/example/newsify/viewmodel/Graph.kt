package com.example.newsify.viewmodel

import android.content.Context
import androidx.room.Room
import com.example.newsify.model.ApiService
import com.example.newsify.model.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//@InstallIn(SingletonComponent::class)
//@Module
object Graph {
    lateinit var database: NewsDatabase

    // Initialize apiService using Retrofit or your preferred method


    // Initialize newsRepository
    val newsRepository by lazy {
        NewsRepository(
            newsifyDao = database.newsifyDao(),
            //apiService = apiService // Pass the required apiService here
        )
    }

    // Function to initialize the database
    fun provide(context: Context) {
        database = Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "newsify.db"
        ).build()
    }

}
