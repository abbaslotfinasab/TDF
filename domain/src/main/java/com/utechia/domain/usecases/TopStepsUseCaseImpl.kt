package com.utechia.domain.usecases

import com.utechia.domain.model.TopStepsModel
import com.utechia.domain.repository.TopStepsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStepsUseCaseImpl @Inject constructor(private val topStepsRepo: TopStepsRepo):TopStepsUseCase<TopStepsModel> {

    override suspend fun execute(): MutableList<TopStepsModel> {
        return topStepsRepo.getTopUser()
    }

}