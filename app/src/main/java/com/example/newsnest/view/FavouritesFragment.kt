package com.example.newsnest.view


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsnest.viewModel.NewsViewModel
import com.example.newsnest.R
import com.example.newsnest.databinding.FragmentFavouritesBinding
import com.example.newsnest.model.Article
import com.google.android.material.snackbar.Snackbar

class FavouritesFragment : Fragment(R.layout.fragment_favourites){
  private lateinit var binding:FragmentFavouritesBinding
  lateinit var newsViewModel: NewsViewModel
  lateinit var newsAdapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentFavouritesBinding.bind(view)

        newsViewModel=(activity as MainActivity).newsViewModel
        setUpRecycler()

        val itemTouchHelperCallBack=object:ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN , ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT,
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val article=newsAdapter.differ.currentList[position]
             newsViewModel.deleteNews(article)
                Snackbar.make(view,"Removed from Favourites",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        newsViewModel.insertNews(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.favouritesRV)
        }
        newsViewModel.getFavouriteNews().observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it)
        })
    }

    private fun setUpRecycler() {
       newsAdapter= NewsAdapter(object : NewsAdapter.onClickListener {
           override fun onItemClickListener(position: Article) {
               val action=FavouritesFragmentDirections.actionFavouritesFragmentToArticleFragment(position)
               findNavController().navigate(action)
           }
       })
        binding.favouritesRV.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(requireContext())
        }
    }

}