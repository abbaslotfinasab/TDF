package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Transaction(

     val area_alias:String? = "",
     val punch_time:String? = ""

):Parcelable
