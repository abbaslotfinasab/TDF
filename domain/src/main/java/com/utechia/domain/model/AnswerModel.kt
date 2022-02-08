package com.utechia.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnswerModel(

    val id: Int?,
    val option: String?,
    val rate: Int?,
    val text: String?,

):Parcelable
