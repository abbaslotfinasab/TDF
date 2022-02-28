package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.SurveyModel
import com.utechia.domain.repository.SurveyRepo
import org.json.JSONArray
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurveyRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,

    ):SurveyRepo {
    override suspend fun getSurveyList(): MutableList<SurveyModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getSurveyList(PagingEnum.Number.page, PagingEnum.Size.page)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data?.list?.map {it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

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

    override suspend fun getEvaluate(): MutableList<SurveyModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getEvaluate(PagingEnum.Number.page, PagingEnum.Size.page)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data?.list?.map {it.toDomain() }!!.toMutableList()
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