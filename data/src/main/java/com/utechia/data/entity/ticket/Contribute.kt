package com.utechia.data.entity.ticket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Contribute(

    var id :Int?,
    var status:String?,

):Parcelable
