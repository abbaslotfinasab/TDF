package com.utechia.data.entity.cart

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.cart.OfficeModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OfficeData (

    val id :Int?,
    val name: String?,
    val isDeleted: Boolean?,
    val locations: MutableList<Location>?


): Parcelable, ResponseObject<OfficeModel> {

    override fun toDomain(): OfficeModel {
        return OfficeModel(id, name, isDeleted,locations?.map { it.toDomain() }!!.toMutableList())
    }
}