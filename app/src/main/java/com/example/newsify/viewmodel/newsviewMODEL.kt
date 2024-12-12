package com.example.newsify.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsify.model.NewsData
import com.example.newsify.model.NewsRepository
import com.example.newsify.model.newsservice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class NewsViewModel (private val newsRepository: NewsRepository
    ) : ViewModel() {


    var _newsState = mutableStateOf(NewsState())
    val exploreState = mutableStateOf(ExploreState())
    val searchloading = mutableStateOf(false)
    //private val paginator = Defaultpaginator(
    //  initalKey = _newsState.value.page,
    //onLoadUpdated = {
    //  _newsState = _newsState.value.copy(
    //    loading = it
    //)
    //},
    //onRequest = {nextPage -> newsRepository.}
    //)

    //lateinit var getAllNewa: Flow<List<NewsData>>

    //lateinit var getAllQuery: Flow<List<NewsData>>

    init {

        fetchQuery("Bitcoin")

    }


    val pagingData = newsRepository.getNewsStream()


    /* private fun fetchNews() {
        viewModelScope.launch {
            try {
                //page size=10, page number=5
                val data = newsservice.getnews(page = 1, pageSize = 10)
                Log.d("Data", "Fetched News: $data")
                Log.d(
                    "NewsResponse",
                    "Fetched News: ${data.articles}"
                )     //yeh article api mai hai
                _newsState.value = _newsState.value.copy(
                    list = data.articles,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                Log.e("NewsError", "Error fetching news: ${e.message}")
                println("Error fetching news: ${e.message}")
                _newsState.value = _newsState.value.copy(
                    loading = false,
                    error = "Error fetching news: ${e.message}"
                )
            }
        }

    }*/
    data class NewsState(
        val loading: Boolean = true,
        val list: List<NewsData> = emptyList(),
        val error: String? = null,


        )


    fun fetchQuery(text: String) {
        searchloading.value = true
        Log.d("text", "Fetched News: $text")
        viewModelScope.launch {
            try {
                val data = newsservice.getQuery(text)
                Log.d("news", "Fetched News: ${data.articles}")
                exploreState.value = exploreState.value.copy(
                    list = data.articles,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                Log.e("NewsError", "Error fetching news: ${e.message}")
                println("Error fetching news: ${e.message}")
                exploreState.value = exploreState.value.copy(
                    loading = false,
                    error = "Error fetching news: ${e.message}"
                )
            } finally {
                searchloading.value = false
            }

        }


    }


    data class ExploreState(
        val loading: Boolean = true,
        val list: List<NewsData> = emptyList(),
        val error: String? = null,
    )

    private val _bookmarkedNews = MutableStateFlow<List<NewsData>>(emptyList())
    val bookmarkedNews: StateFlow<List<NewsData>> = _bookmarkedNews.asStateFlow()

    init {
        fetchBookmarkedItems()
    }

    private fun fetchBookmarkedItems() {
        // Collect the bookmarked items from the repository
        viewModelScope.launch {
            newsRepository.getBookmarkedItem().collect { items ->
                _bookmarkedNews.value = items
            }
        }
    }

    fun addBookmarkNews(newsList: List<NewsData>) {
        // Launch a coroutine in the ViewModel scope
        viewModelScope.launch {
            // Add the new bookmark(s) to the repository
            newsRepository.addbookmarknews(newsList)

            // Get the current list and add the new items at the top
            _bookmarkedNews.value = newsList + _bookmarkedNews.value
        }
    }

    fun getbybookmarkid(id: Long): NewsData? {
        return newsRepository.getbookmarkbyid(id)
    }

    fun deleteById(newsData: NewsData) {
        viewModelScope.launch {
            newsRepository.deleteById(newsData = newsData)
        }
    }
}
