package com.utechia.domain.usecases

import com.utechia.domain.model.TopStepsModel
import com.utechia.domain.repository.TopStepsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStepsUseCaseImpl @Inject constructor(private val topStepsRepo: TopStepsRepo):TopStepsUseCase<TopStepsModel> {

    override suspend fun execute(start: String,end: String): MutableList<TopStepsModel> {
        return topStepsRepo.getTopUser(start,end)
    }

    override suspend fun getChart(start: String, end: String): MutableList<TopStepsModel> {
        return topStepsRepo.getChart(start,end)
    }

}