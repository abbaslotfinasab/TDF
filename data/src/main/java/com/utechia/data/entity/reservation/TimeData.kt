package com.utechia.data.entity.reservation


import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.reservation.TimeModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class TimeData(

    var id :Int?,

    var time:String?,

    var reserved:Boolean?,

    ):Parcelable,ResponseObject<TimeModel> {
    override fun toDomain(): TimeModel {
        return TimeModel(id,time,reserved)
    }
}
