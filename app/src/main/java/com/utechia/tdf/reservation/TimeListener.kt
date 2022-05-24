package com.utechia.tdf.reservation

import androidx.lifecycle.MutableLiveData
import com.utechia.domain.model.reservation.DurationModel

object TimeListener {
    val timeListener = MutableLiveData<MutableSet<DurationModel>>()
}