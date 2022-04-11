package com.utechia.data.entity.ticket

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.data.entity.cart.Floor
import com.utechia.domain.model.ticket.BaseNeedsModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class BaseNeedsData(

    val category: @Contextual @RawValue MutableList<Category>?,
    val ListFloor: @Contextual @RawValue MutableList<Floor>?,
    val Priority: ArrayList<String>?,

    ):Parcelable,ResponseObject<BaseNeedsModel> {
    override fun toDomain(): BaseNeedsModel {
        return BaseNeedsModel(category?.map { it.toDomain() }!!.toMutableList(),ListFloor?.map { it.toDomain() }!!.toMutableList(),Priority)
    }
}
