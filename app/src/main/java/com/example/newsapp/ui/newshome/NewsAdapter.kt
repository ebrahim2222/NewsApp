package com.example.newsapp.ui.newshome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsItemBinding
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Source

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyHolder>(){


    class MyHolder ( itemView:NewsItemBinding) : RecyclerView.ViewHolder(itemView.root){
        val binding:NewsItemBinding = itemView
    }

    lateinit var navController:NavController
    var context:Context?=null
    var newList:ArrayList<Article>?=null
    var searchList:ArrayList<Article>?=null
    fun getData(context:Context,newList:ArrayList<Article>){
        this.newList = newList
        this.context = context
        searchList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding:NewsItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.news_item ,parent ,false)

        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val item = newList!![position]
        holder.binding.model = item
        holder.itemView.setOnClickListener {
            navController = Navigation.findNavController(it)
            val bundle = Bundle()
            val selectedItem = newList!![position]
            bundle.putParcelable("data",selectedItem)
            navController.navigate(R.id.action_newsFragment_to_detailsFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return if(newList!=null) newList!!.size else 0
    }



}