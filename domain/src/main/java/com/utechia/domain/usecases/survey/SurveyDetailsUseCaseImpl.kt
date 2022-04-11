package com.utechia.domain.usecases.survey

import com.utechia.domain.model.survey.SurveyModel
import com.utechia.domain.repository.survey.SurveyDetailsRepo
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SurveyDetailsUseCaseImpl @Inject constructor(private val surveyRepo: SurveyDetailsRepo):
    SurveyDetailsUseCase<SurveyModel> {

    override suspend fun getSurvey(id: Int): MutableList<SurveyModel> {
        return surveyRepo.getSurvey(id)
    }

    override suspend fun postAnswer(answer: JSONArray):MutableList<SurveyModel> {
        return surveyRepo.postAnswer(answer)
    }

}