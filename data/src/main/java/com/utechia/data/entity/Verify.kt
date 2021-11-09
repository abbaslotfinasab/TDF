package com.utechia.data.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@JsonClass(generateAdapter = true)
data class Verify(

    val data :@RawValue VerifyData?,
    val error:@RawValue Error?

):Parcelable