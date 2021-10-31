package com.utechia.domain.repository

import com.utechia.domain.model.SearchModel

interface SearchRepo {

    fun search(name:String):MutableList<SearchModel>

}