package com.example.newsnest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsnest.model.Article
import com.example.newsnest.model.NewsResponse
import com.example.newsnest.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository):ViewModel(){
    init {
        viewModelScope.launch {
            repository.getHeadlines("in", 1)
        }
    }
    val headlinesLiveData:LiveData<NewsResponse>
        get()=repository.headlines
   fun searchNews(query:String){
       viewModelScope.launch {
           repository.searchNews(query,1)
       }
   }
    val searchNewsLiveData:LiveData<NewsResponse>
        get()=repository.searchNews

    fun getFavouriteNews()=repository.getArticles()
    fun deleteNews(article: Article)=viewModelScope.launch{
        repository.delete(article)
    }
    fun insertNews(article: Article)=viewModelScope.launch{
        repository.insert(article)
    }
}