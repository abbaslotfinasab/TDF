package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.SearchModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Search(

    var id:Int?,

    var name:String?,

    var refreshment_id:Int?

):Parcelable, ResponseObject<SearchModel> {
    override fun toDomain(): SearchModel {
        return SearchModel(id,name,refreshment_id)
    }
}