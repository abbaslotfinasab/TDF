package com.utechia.domain.usecases.home

import com.utechia.domain.model.home.TeaBoyActiveModel
import com.utechia.domain.repository.home.TeaBoyActiveRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeaBoyActiveUseCaseImpl @Inject constructor(private val teaBoyActiveRepo: TeaBoyActiveRepo):TeaBoyActiveUseCase<TeaBoyActiveModel> {
    override suspend fun execute(): MutableList<TeaBoyActiveModel> {
        return teaBoyActiveRepo.getActiveList()
    }
}