package com.utechia.data.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.RefreshmentModel
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class Refreshment(

    var id :Int?,

    var category:Int?,

    var name:String?,

    var image:String?,

    var favorit:Boolean?,

    var number:Int?,

    var calorie:Int?,

    var time:String?,

): Parcelable, ResponseObject<RefreshmentModel> {
    override fun toDomain(): RefreshmentModel {
        return RefreshmentModel(id,category,name,image,favorit,number,calorie,time)
    }
}
