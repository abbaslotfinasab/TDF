package com.utechia.data.entity.ticket

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Ticket(

    val data: @Contextual @RawValue TicketPage,
    val error:@Contextual @RawValue Error?

): Parcelable