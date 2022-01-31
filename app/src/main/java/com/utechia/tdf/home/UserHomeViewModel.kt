package com.utechia.tdf.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.NewsModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

            _news.postValue(Result.Loading)
            newsList.clear()
            newsList.add(NewsModel("SAUDI ARABIA ANNOUNCES TOURISM DEVELOPMENT FUND", R.mipmap.news,"https://www.google.com","2017-01-23T10:12:31.484Z"))
            newsList.add(NewsModel("The tourism is set to support the development of large scale mixed-used projects and resorts", R.mipmap.news,"https://www.google.com","2017-01-23T10:12:31.484Z"))
            newsList.add(NewsModel("SAUDI ARABIA ANNOUNCES TOURISM DEVELOPMENT FUND", R.mipmap.news,"https://www.google.com","2017-01-23T10:12:31.484Z"))
            newsList.add(NewsModel("The tourism is set to support the development of large scale mixed-used projects and resorts", R.mipmap.news,"https://www.google.com","2017-01-23T10:12:31.484Z"))

            _news.postValue(Result.Success(newsList))
        }
    }
}