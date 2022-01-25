package com.utechia.domain.model

data class EventModel(

    val capacity: Int?,
    val coverphoto: String?,
    val date_endsign: String?,
    val date_startsign: String?,
    val datestart: String?,
    val datetime: String?,
    val department: String?,
    val description: String?,
    val duration: Int?,
    val eventPlace: String?,
    val guestModels: MutableList<GuestModel>,
    val id: Int?,
    val isDisable: Boolean?,
    val joinnumbr: Int?,
    val signstatus: String?,
    val status: String?,
    val title: String?,
    val type: String?,
    val contribute: String?,
    val contributeId: Int?


)