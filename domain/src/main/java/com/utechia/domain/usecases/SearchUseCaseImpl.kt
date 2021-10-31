package com.utechia.domain.usecases

import com.utechia.domain.model.SearchModel
import com.utechia.domain.repository.SearchRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchUseCaseImpl @Inject constructor(private val searchRepo: SearchRepo):SearchUseCase<SearchModel>
{
    override fun execute(name: String): MutableList<SearchModel> {
        return searchRepo.search(name)
    }
}