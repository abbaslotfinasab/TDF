package com.utechia.data.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.InviteModel
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
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
