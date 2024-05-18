package com.example.newsnest.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsnest.viewModel.NewsViewModel
import com.example.newsnest.R
import com.example.newsnest.databinding.FragmentSearchBinding
import com.example.newsnest.model.Article
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {
private lateinit var binding:FragmentSearchBinding
private lateinit var newsViewModel: NewsViewModel
lateinit var newsAdapter: NewsAdapter
var job:Job?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSearchBinding.bind(view)
        newsAdapter= NewsAdapter(object : NewsAdapter.onClickListener {
            override fun onItemClickListener(article: Article) {
               val action=SearchFragmentDirections.actionSearchFragmentToArticleFragment(article)
                findNavController().navigate(action)
            }
        })
        newsViewModel=(activity as MainActivity).newsViewModel
        binding.searchRV.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(requireContext())
        }
        binding.searchEditText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                         job?.cancel()
                job=viewLifecycleOwner.lifecycleScope.launch {
                    delay(500L)
                    editable?.let {
                        if(editable.toString().isNotEmpty()){
                            newsViewModel.searchNews(editable.toString().trim())
                        }
                    }
                }
            }

        })
        newsViewModel.searchNewsLiveData.observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it.articles)
        })
    }
}