package com.utechia.data.entity.reservation

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.event.InviteModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Invite(

    var id:Int?,

    var name:String?,

    var image:String?,

    var profession:String?,

    var invited:Boolean = false

): Parcelable, ResponseObject<InviteModel> {
    override fun toDomain(): InviteModel {
        return InviteModel(id,name,image,profession,invited)
    }
}
