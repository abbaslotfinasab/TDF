package com.utechia.tdf.reservation

import androidx.lifecycle.MutableLiveData
import com.utechia.domain.model.reservation.RoomModel

object RoomListener {
    val roomListener = MutableLiveData<RoomModel>()
}