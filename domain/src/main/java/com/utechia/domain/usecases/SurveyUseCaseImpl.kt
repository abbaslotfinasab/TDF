package com.utechia.domain.usecases

import com.utechia.domain.model.AnswerModel
import com.utechia.domain.model.SurveyModel
import com.utechia.domain.repository.SurveyRepo
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurveyUseCaseImpl @Inject constructor(private val surveyRepo: SurveyRepo):SurveyUseCase<SurveyModel>{
    override suspend fun getSurveyList(): MutableList<SurveyModel> {
        return surveyRepo.getSurveyList()
    }

    override suspend fun getSurvey(id: Int): MutableList<SurveyModel> {
        return surveyRepo.getSurvey(id)
    }

    override suspend fun getEvaluate(): MutableList<SurveyModel> {
        return surveyRepo.getEvaluate()
    }

    override suspend fun postAnswer(answer: JSONArray):MutableList<SurveyModel> {
        return surveyRepo.postAnswer(answer)
    }
}