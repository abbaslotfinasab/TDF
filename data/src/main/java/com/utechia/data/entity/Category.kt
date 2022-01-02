package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.CategoryModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Category(

    val id:Int?,
    val title:String?,

    ): Parcelable,ResponseObject<CategoryModel> {
    override fun toDomain(): CategoryModel {
        return CategoryModel(
            id, title
        )
    }
}
