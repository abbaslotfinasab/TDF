package com.utechia.data.entity.order

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.data.entity.cart.CartData
import com.utechia.data.entity.cart.Floor
import com.utechia.domain.model.order.UserOrderDataModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserOrderData(

    val id: Int?,
    val status: String?,
    val orderrate: @Contextual @RawValue MutableList<OrderRating>?,
    val floor: Floor?,
    val createdAt: String?,
    val updatedAt: String?,
    val cart: @Contextual @RawValue CartData?,
    val location:String?,



    ):Parcelable,ResponseObject<UserOrderDataModel>{
    override fun toDomain(): UserOrderDataModel {
        return UserOrderDataModel(id,status,orderrate?.map { it.toDomain() }!!.toMutableList(),floor?.name,createdAt,updatedAt,cart?.toDomain(),location)
    }
}
