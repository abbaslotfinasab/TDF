package com.utechia.data.entity.health

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.data.entity.profile.User
import com.utechia.domain.model.health.TopStepsModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TopStepsData(

    val id : Int?,
    val userid: Int?,
    val count: Int?,
    val cal: Int?,
    val createdAt: String?,
    val UpdatedAt: String?,
    val user : User


):Parcelable, ResponseObject<TopStepsModel> {
    override fun toDomain(): TopStepsModel {
        return TopStepsModel(id, userid,count,cal,createdAt,UpdatedAt,user.toDomain())
    }
}