package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.EventModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class EventData(
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
    val guests: @Contextual @RawValue MutableList<Guest>?,
    val id: Int?,
    val isDisable: Boolean?,
    val joinnumbr: Int?,
    val signstatus: String?,
    val status: String?,
    val title: String?,
    val type: String?
):Parcelable, ResponseObject<EventModel> {
    override fun toDomain(): EventModel {
        return EventModel(capacity, coverphoto, date_endsign, date_startsign, datestart, datetime,department,description,duration,eventPlace,guests?.map { it.toDomain() }!!.toMutableList(),id,isDisable,joinnumbr,signstatus,status,title,type)
    }
}