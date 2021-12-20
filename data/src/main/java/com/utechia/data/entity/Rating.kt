package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.RatingModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Rating(

    val id:Int?,
    val rate:Int?

): Parcelable,ResponseObject<RatingModel> {
    override fun toDomain(): RatingModel {
        return RatingModel(id,rate)
    }
}