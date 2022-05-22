package com.utechia.data.entity.cart

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.cart.FloorModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity
data class Floor (
    @PrimaryKey
    val id :Int?,
    val name: String?,
    val isDeleted: Boolean?,


): Parcelable, ResponseObject<FloorModel> {

    override fun toDomain(): FloorModel {
        return FloorModel(id, name, isDeleted)
    }
}