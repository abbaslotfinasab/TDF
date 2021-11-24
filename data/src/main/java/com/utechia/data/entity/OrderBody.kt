package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.OrderBodyModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OrderBody(

    val id:Int?,
    val status:String?

): Parcelable,ResponseObject<OrderBodyModel> {

    override fun toDomain(): OrderBodyModel {
        return OrderBodyModel(id,status)
    }
}
