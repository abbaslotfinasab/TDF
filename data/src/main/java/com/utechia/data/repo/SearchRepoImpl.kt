package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.Search
import com.utechia.domain.model.SearchModel
import com.utechia.domain.repository.SearchRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepoImpl @Inject constructor(
    val service: Service
):SearchRepo {

    private val search:MutableList<Search> = mutableListOf()

    override fun search(name: String): MutableList<SearchModel> {
        search.clear()
        search.add(Search(0,"Special Coffee",0))
        search.add(Search(1,"Special Tea",1))
        return search.map { it.toDomain() }.toMutableList()

    }

}