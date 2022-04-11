package com.utechia.data.repo.survey

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.survey.SurveyModel
import com.utechia.domain.repository.survey.SurveyDetailsRepo
import org.json.JSONArray
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurveyDetailsRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,

    ): SurveyDetailsRepo {

    override suspend fun getSurvey(id: Int): MutableList<SurveyModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getSurvey(id)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun postAnswer(answer: JSONArray): MutableList<SurveyModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.postAnswer(answer.toString())

            return when (result.isSuccessful) {

                true -> {
                    emptyList<SurveyModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}