package com.utechia.data.entity.reservation

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utechia.data.BuildConfig
import com.utechia.data.base.ResponseObject
import com.utechia.data.entity.cart.Floor
import com.utechia.domain.model.cart.FloorModel
import com.utechia.domain.model.reservation.RoomModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity
data class RoomData(
    @PrimaryKey
    val id: Int?,
    val active: Boolean?,
    val building: String?,
    val capacity: Int?,
    val city: String?,
    val country: String?,
    val coverPhoto: String?,
    val createdAt: String?,
    val isDeleted: Boolean?,
    val mailbox: String?,
    val name: String?,
    val type: String?,
    val updatedAt: String?,
    @Embedded(prefix = "prefix_")
    val floor:Floor?
):Parcelable, ResponseObject<RoomModel> {
    override fun toDomain(): RoomModel {
        return RoomModel(
            active,
            building,
            capacity,
            city,
            country,
            "${BuildConfig.BASE_URL}$coverPhoto",
            createdAt,
            id,
            isDeleted,
            mailbox,
            name,
            type,
            updatedAt,
            floor?.toDomain()?: FloorModel(0,"",true)
        )
    }
}