package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.VerifyModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class VerifyData(

    val token: String?,
    val userHomeId: String?,
    val isTeaBoy :Boolean?,
    val isTeaBoyActive : Boolean?,
    val floor : Int?,
    val name: String?,
    val jobTitle:String?

):Parcelable, ResponseObject<VerifyModel> {
    override fun toDomain(): VerifyModel {
        return VerifyModel(token, userHomeId,isTeaBoy,isTeaBoyActive,floor,name,jobTitle)
    }
}