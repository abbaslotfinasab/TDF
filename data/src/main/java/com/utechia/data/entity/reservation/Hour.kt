package com.utechia.data.entity.reservation


import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.reservation.HourModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Hour(

    var id :Int,

    var title:String,

    var available:Boolean,


    ):Parcelable,ResponseObject<HourModel> {
    override fun toDomain(): HourModel {
        return HourModel(id,title,available)
    }
}
