package com.utechia.data.entity.order

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.order.RatingModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class OrderRating(

    var id:Int?,
    var rate:Int?

): Parcelable,ResponseObject<RatingModel> {
    override fun toDomain(): RatingModel {
        return RatingModel(id,rate)
    }
}