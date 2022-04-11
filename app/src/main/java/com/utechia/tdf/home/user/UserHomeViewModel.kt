package com.utechia.tdf.home.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.home.NewsModel
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserHomeViewModel @Inject constructor(


): ViewModel() {

    private val newsList:MutableList<NewsModel> = mutableListOf()
    private val _news = MutableLiveData<Result<MutableList<NewsModel>>>()
    val news: LiveData<Result<MutableList<NewsModel>>>
        get() = _news

    private val handler = CoroutineExceptionHandler { _, exception ->
        _news.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getNews() {

        viewModelScope.launch(Dispatchers.IO + handler) {


        }
    }
}