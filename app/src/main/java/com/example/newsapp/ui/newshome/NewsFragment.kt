package com.example.newsapp.ui.newshome

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.domain.model.Article
import com.example.newsapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class NewsFragment : Fragment() {
    @Inject lateinit var newsAdapter: NewsAdapter
    val newsViewModel:NewsViewModel by viewModels()
    lateinit var binding:FragmentNewsBinding
    var searchList = ArrayList<Article>()
    var newsArticle:ArrayList<Article> ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater)
        val view = binding.root
        setUpRecycler()
        getNews()

        return view
    }

    private fun getNews() {
        lifecycleScope.launchWhenCreated {
            newsViewModel.getNews()
            newsViewModel.mutableStateFlow.collect {
                when(it.status){
                    Status.SUCCESS -> {
                        newsArticle =  it.data!!.articles as ArrayList<Article>
                        searchList.addAll(newsArticle!!)
                        newsAdapter.getData(requireContext(),
                            searchList
                        )
                        newsAdapter.notifyDataSetChanged()
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                filterText(searchText)
                return false
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterText(searchText: String) {
        if(searchText.isNotEmpty()){
            newsArticle!!.forEach {
                if(it.title!!.lowercase(Locale.getDefault()).contains(searchText)){
                    searchList.add(it)
                }
            }
            newsAdapter.notifyDataSetChanged()
        }else{
            searchList.clear()
            searchList.addAll(newsArticle!!)
            newsAdapter.notifyDataSetChanged()
        }

    }


    private fun setUpRecycler() {
        binding.newsRv.adapter = newsAdapter
    }

}