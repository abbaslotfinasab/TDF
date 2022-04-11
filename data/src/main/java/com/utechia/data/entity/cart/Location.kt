package com.utechia.data.entity.cart

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.cart.LocationModel
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

