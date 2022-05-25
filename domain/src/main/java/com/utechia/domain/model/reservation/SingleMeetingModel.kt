package com.utechia.domain.model.reservation

import com.utechia.domain.model.profile.ProfileModel

data class SingleMeetingModel(
    val id:Int?,
    val subject:String?,
    val description:String?,
    val date:String?,
    val startsAt:String?,
    val endsAt:String?,
    val room:RoomModel?,
    val duration:Int?,
    val status:String?,
    val presenter:ProfileModel,
    val attendees:MutableList<ProfileModel>,
    val guests:MutableList<GuestsModel>,
)
