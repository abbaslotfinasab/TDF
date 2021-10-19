package com.utechia.data.entity


import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.HourModel
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class Hour(

    var id :Int,

    var title:String,

    var available:Boolean,


    ):Parcelable,ResponseObject<HourModel> {
    override fun toDomain(): HourModel {
        return HourModel(id,title,available)
    }
}
