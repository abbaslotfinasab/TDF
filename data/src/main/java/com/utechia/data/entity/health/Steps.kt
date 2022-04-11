package com.utechia.data.entity.health

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Steps(

    var count:Int?,
    var cal:Int?,
    var date:String?,

):Parcelable
