package com.utechia.data.entity.refreshment

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utechia.data.BuildConfig
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.refreshment.RefreshmentModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity
data class RefreshmentData(

    @PrimaryKey
    val id: Int?,
    val category: String?,
    val createdAt: String?,
    val imageName: String?,
    val imagePath: String?,
    val rating: Int?,
    val status: Boolean?,
    val title: String?,
    val isFavorite:Boolean?,
    val updatedAt: String?,
    val open:Boolean?,
    val number:Int?,

):Parcelable , ResponseObject<RefreshmentModel> {
    override fun toDomain(): RefreshmentModel {
        return RefreshmentModel(category,createdAt,id,"${BuildConfig.BASE_URL}/api/cafeteria/image/${imageName}",imagePath,rating,status,title,updatedAt,isFavorite,
            open,number?:0
        )
    }
}                                                                