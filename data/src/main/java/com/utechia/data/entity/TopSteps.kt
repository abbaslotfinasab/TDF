package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.VerifyModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TopSteps(

    val data :@Contextual TopStepsData?,
    val error:@Contextual Error?


    ):Parcelable
