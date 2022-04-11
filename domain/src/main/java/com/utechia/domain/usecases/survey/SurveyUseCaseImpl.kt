package com.utechia.domain.usecases.survey

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.survey.SurveyModel
import com.utechia.domain.repository.survey.SurveyRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurveyUseCaseImpl @Inject constructor(private val surveyRepo: SurveyRepo):
    SurveyUseCase<LiveData<PagingData<SurveyModel>>> {

    override suspend fun getSurveyList(status:String): LiveData<PagingData<SurveyModel>> {
        return surveyRepo.getSurveyList(status)
    }
}