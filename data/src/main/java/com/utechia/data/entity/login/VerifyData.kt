package com.utechia.data.entity.login

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.login.VerifyModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class VerifyData(

    val token: String?,
    val userHomeId: String?,
    val isTeaBoy :Boolean?,
    val floor : Int?,
    val name: String?,
    val jobTitle:String?,
    val mail:String?,
    val userid: Int?,
    val employeeId: Int?,
    val officeWorkStation: String?,
    val officeLocation: String?,

    ):Parcelable, ResponseObject<VerifyModel> {
    override fun toDomain(): VerifyModel {
        return VerifyModel(token, userHomeId,isTeaBoy,floor,name,jobTitle,mail,userid,employeeId,officeWorkStation,officeLocation)
    }
}