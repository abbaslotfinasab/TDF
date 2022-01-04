package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.BaseNeedsModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class BaseNeedsData(

    val category: @Contextual @RawValue MutableList<Category>?,
    val ListFloor: ArrayList<String>?,
    val Priority: ArrayList<String>?,

    ):Parcelable,ResponseObject<BaseNeedsModel> {
    override fun toDomain(): BaseNeedsModel {
        return BaseNeedsModel(category?.map { it.toDomain() }!!.toMutableList(),ListFloor,Priority)
    }
}
