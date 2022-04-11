package com.utechia.domain.repository.survey

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.survey.SurveyModel

interface SurveyRepo {

    suspend fun getSurveyList(status:String): LiveData<PagingData<SurveyModel>>


}