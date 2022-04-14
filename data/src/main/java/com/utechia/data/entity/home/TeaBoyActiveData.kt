package com.utechia.data.entity.home

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.home.TeaBoyActiveModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TeaBoyActiveData(

    val id: Int?,
    val cafeteriaStatus: Boolean?,
    val name: String?,


    ): Parcelable, ResponseObject<TeaBoyActiveModel> {
    override fun toDomain(): TeaBoyActiveModel {
        return TeaBoyActiveModel(id,name,cafeteriaStatus)
    }
}
