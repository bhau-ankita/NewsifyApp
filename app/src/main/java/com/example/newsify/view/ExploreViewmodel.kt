package com.example.newsify.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsify.Screen.NewsifyDao
import com.example.newsify.model.NewsData
import com.example.newsify.model.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ExploreViewModel(
    private  val newsRepository: NewsRepository )
    : ViewModel() {


    private val _bookmarkedItems = MutableStateFlow<List<NewsData>>(emptyList())
    val bookmarkedItems: StateFlow<List<NewsData>> = _bookmarkedItems

    init {
        fetchBookmarkedItems()
    }

    private fun fetchBookmarkedItems() {
        viewModelScope.launch {
            newsRepository.getBookmarkedItem().collect { items ->
                _bookmarkedItems.value = items
            }
        }
    }

   // fun updateBookmark(newsId: String, status: String) {
     //   viewModelScope.launch {
       //     newsRepository.updateBookmarkStatus(newsId, status)
        //}
    //}
    fun toggleBookmarkById(news: NewsData, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val currentItems = _bookmarkedItems.value.toMutableList()
                if (news.bookmarked ) {
                    // Add the item to the list
                    currentItems.add(news)
                } else {
                    // Remove the item from the list by matching the id
                    currentItems.removeAll { it.id == news.id }
                }
                _bookmarkedItems.value = currentItems

                onComplete(true)
                // newsRepository.getByBookmarked(news.id)
            } catch (e: Exception) {
                onComplete(false)
            }
        }
    }
}




