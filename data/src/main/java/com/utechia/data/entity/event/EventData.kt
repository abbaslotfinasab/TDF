package com.utechia.data.entity.event

import android.os.Parcelable
import com.utechia.data.BuildConfig
import com.utechia.data.base.ResponseObject
import com.utechia.data.entity.ticket.Contribute
import com.utechia.domain.model.event.EventModel
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
    val isPublic: Boolean?,
    val joinnumbr: Int?,
    val signstatus: String?,
    val status: String?,
    val title: String?,
    val type: String?,
    val userrate: Int?,
    val contribut: Contribute?
):Parcelable, ResponseObject<EventModel> {
    override fun toDomain(): EventModel {
        return EventModel(capacity, "${BuildConfig.BASE_URL}$coverphoto", date_endsign, date_startsign, datestart, datetime,department,description,duration,eventPlace,guests?.map { it.toDomain() }!!.toMutableList(),id,isDisable,isPublic,joinnumbr,signstatus,status,title,type,contribut?.status,contribut?.id,userrate)
    }
}