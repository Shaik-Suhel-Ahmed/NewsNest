package com.example.newsnest.view


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsnest.viewModel.NewsViewModel
import com.example.newsnest.R
import com.example.newsnest.databinding.FragmentHeadlinesBinding
import com.example.newsnest.model.Article


class HeadlinesFragment : Fragment(R.layout.fragment_headlines), NewsAdapter.onClickListener {
private lateinit var binding: FragmentHeadlinesBinding
    private lateinit var newsAdapter: NewsAdapter
    lateinit var newsViewModel: NewsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHeadlinesBinding.bind(view)
        newsAdapter= NewsAdapter(this)
        newsViewModel=(activity as MainActivity).newsViewModel
        newsViewModel.headlinesLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                newsAdapter.differ.submitList(it.articles)
            }
        })
        binding.headlinesRV.adapter=newsAdapter
        binding.headlinesRV.layoutManager=LinearLayoutManager(requireContext())

    }

    override fun onItemClickListener(article: Article) {
        val action = HeadlinesFragmentDirections.actionHeadlinesFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}