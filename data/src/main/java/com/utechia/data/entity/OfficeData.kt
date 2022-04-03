package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.OfficeModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OfficeData (

    val id :Int?,
    val name: String?,
    val active: Boolean?,
    val floor: String?,

    ): Parcelable, ResponseObject<OfficeModel> {

    override fun toDomain(): OfficeModel {
        return OfficeModel(name, active, floor)
    }
}