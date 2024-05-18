package com.example.newsnest.api


import com.example.newsnest.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI{
    @GET("v2/top-headlines")
   suspend fun getHeadlines(
       @Query("country")
       countryCode:String="in",
       @Query("page")
       pageNumber:Int=1,
        @Query("apiKey")
    apiKey:String="6f3dcffc52aa4d0aa06830fe53647f44"
   ):Response<NewsResponse>

   @GET("v2/everything")
  suspend fun searchNews(
       @Query("q")
      searchQuery:String,
       @Query("page")
       pageNumber:Int=1,
       @Query("apiKey")
       apiKey: String="6f3dcffc52aa4d0aa06830fe53647f44"
   ):Response<NewsResponse>
}