package com.utechia.data.entity.reservation


import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class SingleMeeting(
    val data :@Contextual @RawValue SingleMeetingData?,
    val error:@Contextual @RawValue Error?
):Parcelable