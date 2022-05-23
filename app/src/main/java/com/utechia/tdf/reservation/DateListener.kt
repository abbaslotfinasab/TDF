package com.utechia.tdf.reservation

import androidx.lifecycle.MutableLiveData
import com.utechia.domain.model.reservation.DateModel

object DateListener {
    val dateAdapterListener = MutableLiveData<DateModel?>()
    val datePickerListener = MutableLiveData<DateModel?>()

}