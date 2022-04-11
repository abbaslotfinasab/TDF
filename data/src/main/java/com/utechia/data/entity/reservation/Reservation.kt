package com.utechia.data.entity.reservation

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.reservation.ReservationModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Reservation(
    var id:Int?,

    var title:String?,

    var capacity:Int?,

    var room_id:Int?,

    var day:String?,

    var month:String?,

    var year:String?,

    var starTime:String?,

    var endTime:String?,

    var duration:String?,

    var description:String?,

    var invite: MutableList<Invite>

) : Parcelable, ResponseObject<ReservationModel> {
    override fun toDomain(): ReservationModel {
        return ReservationModel(id,title,capacity,room_id,day,month,year,starTime,endTime,duration,description,invite.map { it.toDomain() }.toMutableList())
    }
}
