package com.utechia.data.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.LoginModel
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class LoginData(

    val url: String?

):Parcelable , ResponseObject<LoginModel> {
    override fun toDomain(): LoginModel {
        return LoginModel(url)
    }
}