package com.utechia.tdf.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.utechia.domain.model.NotificationModel
import com.utechia.domain.utile.Result

object NotificationListener {
    val _notificationListener = MutableLiveData<Boolean>()
}