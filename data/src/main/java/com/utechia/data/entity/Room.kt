package com.utechia.data.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.RoomModel
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
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
