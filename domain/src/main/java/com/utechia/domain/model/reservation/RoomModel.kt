package com.utechia.domain.model.reservation

import com.utechia.domain.model.cart.FloorModel

data class RoomModel(
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
     val floor:FloorModel
)
