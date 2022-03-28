package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.OfficeModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OfficeData (

    val location: String?,
    val active: String?,
    val floor: String?,
    val zone: String?,
    val workStation: String?,


    ): Parcelable, ResponseObject<OfficeModel> {

    override fun toDomain(): OfficeModel {
        return OfficeModel(location, active, floor, zone, workStation)
    }
}