package com.example.newsify.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.net.URL
import java.util.Date

@Parcelize
@Entity(tableName = "NewsTable")
data class NewsData(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: Date,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "urlToImage")
    val urlToImage :String,
    @ColumnInfo(name = "content")
    val content : String,
    @ColumnInfo(name ="bookmark")
    var bookmarked: Boolean = false,
    @ColumnInfo(name = "toggling")
    val isToggling :Boolean = false,
    @ColumnInfo(name = "bookmarkStatus")
    var bookmarkStatus: String = "Unmarked",

    ):Parcelable

data class NewsResponse(val articles :List<NewsData>, val totalResults: Int,) // yeh api ke array ko call lr rh hai jisma data hai
