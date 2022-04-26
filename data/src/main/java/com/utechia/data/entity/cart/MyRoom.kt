package com.utechia.data.entity.cart

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.cart.MyRoomModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MyRoom (
    val officeWorkStation: String?,

    ):Parcelable, ResponseObject<MyRoomModel> {

    override fun toDomain(): MyRoomModel {
        return MyRoomModel(officeWorkStation)
    }
}