package com.example.newsnest.view


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsnest.viewModel.NewsViewModel
import com.example.newsnest.R
import com.example.newsnest.databinding.FragmentArticleBinding
import com.example.newsnest.model.Article
import com.google.android.material.snackbar.Snackbar


class ArticleFragment : Fragment(R.layout.fragment_article){
   private lateinit var binding:FragmentArticleBinding
    private lateinit var newsViewModel: NewsViewModel
   private val args:ArticleFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentArticleBinding.bind(view)
        newsViewModel = (activity as? MainActivity)?.newsViewModel
            ?: throw IllegalStateException("Activity must be MainActivity")
        val article=args.article
             webViewSetUP(binding.articleFragmentWebView,article)
        binding.FAB.setOnClickListener {
            article.let {
                newsViewModel.insertNews(article)
                Snackbar.make(view,"Added To Favourites",Snackbar.LENGTH_LONG).show()
                binding.FAB.backgroundTintList=ContextCompat.getColorStateList(requireContext(),
                    R.color.new1
                )
            }
        }
        }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetUP(webView: WebView, article: Article) {
           webView.webViewClient= WebViewClient()
           webView.settings.javaScriptEnabled = true
           article.url?.let {
               if(it.isNotEmpty()) {
                   webView.loadUrl(it)
               }
}
}
}