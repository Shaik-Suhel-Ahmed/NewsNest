package com.example.newsnest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsnest.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict=OnConflictStrategy.IGNORE)
    suspend fun insert(article: Article):Long
    @Delete
    suspend fun delete(article: Article)
    @Query("SELECT * FROM newsArticles")
     fun getArticles():LiveData<List<Article>>
}