package com.utechia.data.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.VerifyModel
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class VerifyData(

    val token: String?,
    val userHomeId: String?

):Parcelable, ResponseObject<VerifyModel> {
    override fun toDomain(): VerifyModel {
        return VerifyModel(token, userHomeId)
    }
}