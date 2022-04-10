package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.LocationModel
import com.utechia.domain.model.OfficeModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Location(

    val id :Int?,
    val name: String?,
    val active: Boolean?,

    ): Parcelable, ResponseObject<LocationModel> {

    override fun toDomain(): LocationModel {
        return LocationModel(id, name, active)
    }
}

