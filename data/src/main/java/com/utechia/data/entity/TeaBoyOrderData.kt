package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.TeaBoyOrderDataModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TeaBoyOrderData(

    val id: Int?,
    val status: String?,
    val floor: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val cart: @Contextual @RawValue CartData?,
    val user: @Contextual @RawValue User?



    ): Parcelable, ResponseObject<TeaBoyOrderDataModel> {
    override fun toDomain(): TeaBoyOrderDataModel {
        return TeaBoyOrderDataModel(id,status,floor,createdAt,updatedAt,cart?.toDomain(),user?.toDomain())
    }
}
