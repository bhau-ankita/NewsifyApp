package com.example.newsify.viewmodel

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsify.Screen.NewsifyDao
import com.example.newsify.model.NewsData

@Database(
    entities = [NewsData::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NewsDatabase :RoomDatabase(){
    abstract fun newsifyDao(): NewsifyDao
}