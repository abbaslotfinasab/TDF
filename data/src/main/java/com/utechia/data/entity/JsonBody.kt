package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import org.json.JSONArray

@Parcelize
data class JsonBody(
    val data:@RawValue JSONArray
):Parcelable
