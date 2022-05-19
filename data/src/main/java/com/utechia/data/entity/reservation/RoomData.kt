package com.utechia.data.entity.reservation

import android.os.Parcelable
import com.utechia.data.entity.cart.Floor
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class RoomData(
    val active: Boolean?,
    val building: String?,
    val capacity: Int?,
    val city: String?,
    val country: String?,
    val coverPhoto: String?,
    val createdAt: String?,
    val id: Int?,
    val isDeleted: Boolean?,
    val mailbox: String?,
    val name: String?,
    val type: String?,
    val updatedAt: String?,
    val floor:Floor
):Parcelable