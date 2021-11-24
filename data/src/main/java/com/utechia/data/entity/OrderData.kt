package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.OrderDataModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OrderData(

    val id: Int?,
    val status: String?,
    val rating: Int?,
    val floor: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val cart: @Contextual @RawValue CartData,


):Parcelable,ResponseObject<OrderDataModel>{
    override fun toDomain(): OrderDataModel {
        return OrderDataModel(id,status,rating,floor,createdAt,updatedAt,cart.toDomain())
    }
}
