package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.serialization.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Serializable
data class Login(

    val data: @Contextual LoginData?,
    val error:@Contextual Error?

):Parcelable