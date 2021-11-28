package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.OrderCountModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OrderCountData (

    val pending: String?,

    val cancelled: String?,

    val delivered: String?

    ):Parcelable,ResponseObject<OrderCountModel>{
    override fun toDomain(): OrderCountModel {
        return OrderCountModel(pending,cancelled,delivered)
    }


}