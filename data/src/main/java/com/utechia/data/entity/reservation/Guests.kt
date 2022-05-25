package com.utechia.data.entity.reservation

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.reservation.GuestsModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Guests(
    var id:Int?,
    var name:String?,
    var mail:String?,
    var job:String?,
):Parcelable, ResponseObject<GuestsModel> {
    override fun toDomain(): GuestsModel {
        return GuestsModel(name, mail, job)
    }
}
