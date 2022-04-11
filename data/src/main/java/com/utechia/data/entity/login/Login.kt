package com.utechia.data.entity.login

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.serialization.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Serializable
data class Login(

    val data: @Contextual LoginData?,
    val error:@Contextual Error?

):Parcelable