package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.RoomModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Room(

    var id:Int?,

    var name:String?,

    var floor:String?,

    var capacity:Int?,

    var hour:MutableList<Hour>



):Parcelable , ResponseObject<RoomModel> {
    override fun toDomain(): RoomModel {
        return RoomModel(id,name,floor,capacity,hour.map {it.toDomain()}.toMutableList())
    }
}
