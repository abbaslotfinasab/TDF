package com.utechia.data.entity.reservation

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.data.entity.profile.ProfileData
import com.utechia.domain.model.reservation.SingleMeetingModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class SingleMeetingData(
    val id:Int?,
    val subject:String?,
    val description:String?,
    val date:String?,
    val startsAt:String?,
    val endsAt:String?,
    val room:RoomData?,
    val duration:Int?,
    val status:String?,
    val presenter:ProfileData?,
    val attendees:MutableList<ProfileData>?,
    val guests:MutableList<Guests>?,
):Parcelable, ResponseObject<SingleMeetingModel> {
    override fun toDomain(): SingleMeetingModel {
        return SingleMeetingModel(
            id,
            subject,
            description,
            date,
            startsAt,
            endsAt,
            room!!.toDomain(),
            duration,
            status,
            presenter!!.toDomain(),
            attendees?.map { it.toDomain() }!!.toMutableList(),
            guests?.map { it.toDomain() }!!.toMutableList()
        )
    }
}
