package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.UserOrderDataModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserOrderData(

    val id: Int?,
    val status: String?,
    val orderrate: @Contextual @RawValue MutableList<Rating>?,
    val floor: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val cart: @Contextual @RawValue CartData?,


    ):Parcelable,ResponseObject<UserOrderDataModel>{
    override fun toDomain(): UserOrderDataModel {
        return UserOrderDataModel(id,status,orderrate?.map { it.toDomain() }!!.toMutableList(),floor,createdAt,updatedAt,cart?.toDomain())
    }
}
