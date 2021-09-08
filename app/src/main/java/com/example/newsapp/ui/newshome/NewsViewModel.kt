package com.example.newsapp.ui.newshome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repositoryimp.RepositoryImp
import com.example.newsapp.domain.model.NewsResponse
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val repositoryImp: RepositoryImp
) : ViewModel() {
    val mutableStateFlow = MutableStateFlow<Resource<NewsResponse>>(Resource.loading(null))
    var response:NewsResponse?= null
    fun getNews(){
        viewModelScope.launch {
            try {
                response = repositoryImp.api.getNews("eg","63b1f94dad044add871d1e319c630265")
                mutableStateFlow.value = Resource.success(response)
            }catch (e:Exception){
                mutableStateFlow.value = Resource.error(e.message,response)
            }
        }
    }

}