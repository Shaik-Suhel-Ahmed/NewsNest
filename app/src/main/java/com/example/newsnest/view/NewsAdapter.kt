package com.example.newsnest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsnest.R
import com.example.newsnest.model.Article


class NewsAdapter(private val listener: onClickListener):RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){
    interface onClickListener{
        fun onItemClickListener(article: Article)
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var articleImage:ImageView=itemView.findViewById(R.id.article_Image)
        var articleSource:TextView=itemView.findViewById(R.id.articleSource)
         var articleTitle:TextView=itemView.findViewById(R.id.articleTitle)
        var articleDateAndTime:TextView=itemView.findViewById(R.id.articleDateTime)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false))
    }
    private val differCallBack=object :DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
         val differ=AsyncListDiffer(this,differCallBack)
    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentArticle=differ.currentList[position]
           holder.apply {
               Glide.with(holder.itemView.context).load(currentArticle.urlToImage).into(holder.articleImage)
               articleSource.text=currentArticle.source?.name.orEmpty()
               articleTitle.text=currentArticle.title
               articleDateAndTime.text=currentArticle.publishedAt
itemView.setOnClickListener {
    listener.onItemClickListener(differ.currentList[position])
}
           }
    }
}