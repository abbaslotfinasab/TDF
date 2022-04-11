package com.utechia.data.entity.cart

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.cart.FloorModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Floor (

    val id :Int?,
    val name: String?,
    val isDeleted: Boolean?,


): Parcelable, ResponseObject<FloorModel> {

    override fun toDomain(): FloorModel {
        return FloorModel(id, name, isDeleted)
    }
}