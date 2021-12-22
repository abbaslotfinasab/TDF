package com.utechia.domain.usecases

import com.utechia.domain.repository.SurveyAnswerRepo
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurveyAnswerUseCaeImpl @Inject constructor(private val surveyAnswerRepo: SurveyAnswerRepo):SurveyAnswerUseCase {

    override suspend fun postAnswer(answer: JSONArray):Boolean {
        return surveyAnswerRepo.postAnswer(answer)
    }
}