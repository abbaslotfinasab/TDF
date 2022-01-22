package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.GuestModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Guest(
    val avatar: String?,
    val description: String?,
    val fullname: String?,
    val jobtitle: String?,
    val linkedin: String?,
    val weblink: String?
):Parcelable,ResponseObject<GuestModel> {
    override fun toDomain(): GuestModel {
        return GuestModel(avatar, description, fullname, jobtitle, linkedin, weblink)
    }
}