package com.utechia.data.entity.reservation

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.reservation.MeetingModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MeetingData (
    val id:Int?,
    val subject:String?,
    val description:String?,
    val date:String?,
    val startsAt:String?,
    val endsAt:String?,
    val room:RoomData?,
    val duration:Int?,
    val status:String?,


    ): Parcelable, ResponseObject<MeetingModel> {
    override fun toDomain(): MeetingModel {
        return MeetingModel(id,subject,description,room?.toDomain(),date,startsAt,endsAt,duration,status)
    }
}