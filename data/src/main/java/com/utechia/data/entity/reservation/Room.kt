package com.utechia.data.entity.reservation

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.data.entity.refreshment.RefreshmentData
import com.utechia.domain.model.reservation.RoomModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Room(
    val data: @Contextual @RawValue MutableList<RoomData>?,
    val error:  @Contextual @RawValue Any?
):Parcelable
