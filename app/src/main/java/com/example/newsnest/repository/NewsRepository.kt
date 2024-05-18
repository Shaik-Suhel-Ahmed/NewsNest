package com.example.newsnest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsnest.api.NewsAPI
import com.example.newsnest.database.ArticleDataBase
import com.example.newsnest.model.Article
import com.example.newsnest.model.NewsResponse

class NewsRepository(private val newsAPI: NewsAPI,private val db:ArticleDataBase) {
    private val headlinesLiveData = MutableLiveData<NewsResponse>()
    val headlines: LiveData<NewsResponse>
        get() = headlinesLiveData
    private val searchLiveData=MutableLiveData<NewsResponse>()
            val searchNews:LiveData<NewsResponse>
            get()=searchLiveData

    suspend fun getHeadlines(countryCode: String, page: Int) {
        val response = newsAPI.getHeadlines(countryCode, page)
        if (response.body() != null) {
            headlinesLiveData.postValue(response.body())
        }
    }
    suspend fun searchNews(searchQuery:String,pageNumber:Int){
        val response=newsAPI.searchNews(searchQuery,pageNumber)
        if(response.body()!=null){
            searchLiveData.postValue(response.body())
        }
    }
      suspend fun insert(article:Article){
           db.getArticleDao().insert(article)
}
    suspend fun delete(article:Article){
        db.getArticleDao().delete(article)
    }
       fun getArticles()=db.getArticleDao().getArticles()
}