package com.utechia.data.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.RoomModel
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class Room(

    var id:Int?,

    var name:String?,

    var capacity:Int?,

    var hour:MutableList<Hour>



):Parcelable , ResponseObject<RoomModel> {
    override fun toDomain(): RoomModel {
        return RoomModel(id,name,capacity,hour.map {it.toDomain()}.toMutableList())
    }
}
