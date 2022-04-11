package com.utechia.data.entity.login

import android.os.Parcelable
import com.utechia.data.entity.main.Error

import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Verify(

    val data :@Contextual VerifyData?,
    val error:@Contextual Error?

):Parcelable