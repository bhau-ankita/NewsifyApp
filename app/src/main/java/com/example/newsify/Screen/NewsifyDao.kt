package com.example.newsify.Screen

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newsify.model.NewsData
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NewsifyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addNews(newsData: List<NewsData>)

    @Query("Select * from `NewsTable`")
    abstract fun getAllNews(): Flow<List<NewsData>>

@Query("Select * from `NewsTable`")
abstract  fun getAllQuery() :Flow<List<NewsData>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addbookmarknews(newsData: List<NewsData>)

    @Query("SELECT * FROM `NewsTable` WHERE id = :newsId ")
   abstract  fun getBookmarkById(newsId: Long): NewsData?

    @Query("SELECT * FROM `NewsTable` WHERE bookmarkStatus = :status")
    abstract fun getBookmarkedItems(status: String = "Marked"): Flow<List<NewsData>>

    @Query("UPDATE `NewsTable`SET bookmarkStatus = :status WHERE id = :newsId")
   abstract suspend fun updateBookmarkStatus(newsId: String, status: String)

   // @Query("DELETE FROM `NewsTable`")
   //abstract suspend fun deleteItem(newsData: NewsData)

    @Query("DELETE FROM `NewsTable` WHERE id = :id")
    abstract suspend fun deleteById(id: Long)





}