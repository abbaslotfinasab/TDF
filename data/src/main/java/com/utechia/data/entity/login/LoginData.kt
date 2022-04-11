package com.utechia.data.entity.login

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.login.LoginModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LoginData(

    val url: String?

):Parcelable , ResponseObject<LoginModel> {
    override fun toDomain(): LoginModel {
        return LoginModel(url)
    }
}