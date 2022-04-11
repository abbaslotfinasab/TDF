package com.utechia.data.entity.survey

import android.os.Parcelable
import com.utechia.data.entity.main.Error

import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SingleSurvey(

    val data :@Contextual @RawValue MutableList<SurveyData>,
    val error:@Contextual Error?

):Parcelable