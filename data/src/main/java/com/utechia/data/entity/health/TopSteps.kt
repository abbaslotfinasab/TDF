package com.utechia.data.entity.health

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TopSteps(

    val data :@Contextual MutableList<TopStepsData>?,
    val error:@Contextual Error?


    ):Parcelable
