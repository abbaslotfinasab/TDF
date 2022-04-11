package com.utechia.domain.usecases.health

import com.utechia.domain.model.health.TopStepsModel
import com.utechia.domain.repository.health.TopStepsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStepsUseCaseImpl @Inject constructor(private val topStepsRepo: TopStepsRepo):
    TopStepsUseCase<TopStepsModel> {

    override suspend fun execute(start: String,end: String): MutableList<TopStepsModel> {
        return topStepsRepo.getTopUser(start,end)
    }

    override suspend fun getChart(start: String, end: String): MutableList<TopStepsModel> {
        return topStepsRepo.getChart(start,end)
    }

}