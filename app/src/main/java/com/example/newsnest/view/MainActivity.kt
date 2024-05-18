package com.example.newsnest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsnest.repository.NewsRepository
import com.example.newsnest.viewModel.NewsViewModel
import com.example.newsnest.viewModel.NewsViewModelFactory
import com.example.newsnest.R
import com.example.newsnest.api.NewsAPI
import com.example.newsnest.api.RetrofitInstance
import com.example.newsnest.database.ArticleDataBase
import com.example.newsnest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
   lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val instance= RetrofitInstance.getInstance().create(NewsAPI::class.java)
        val repository= NewsRepository(instance,ArticleDataBase(this))
        newsViewModel= ViewModelProvider(this, NewsViewModelFactory(repository)).get(NewsViewModel::class.java)
      val navHostFragment=supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val navController=navHostFragment.navController
        binding.BottomNav.setupWithNavController(navController)
    }
}