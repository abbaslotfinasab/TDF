package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.repository.SurveyAnswerRepo
import org.json.JSONArray
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurveyAnswerRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,

    ):SurveyAnswerRepo {
    override suspend fun postAnswer(answer: JSONArray): Boolean {

        if (networkHelper.isNetworkConnected()) {

            val result = service.postAnswer(answer)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data!!.success
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}