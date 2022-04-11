package com.utechia.data.entity.favorite

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Favorite(
    val data: @Contextual @RawValue MutableList<FavoriteData>?,
    val error:  @Contextual @RawValue Error?
) : Parcelable