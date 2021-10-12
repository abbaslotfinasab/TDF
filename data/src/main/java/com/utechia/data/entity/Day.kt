package com.utechia.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
@Entity
data class Day(

    @PrimaryKey(autoGenerate = true)
    @Json(name = "id")
    var id :Int?,

    @ColumnInfo(name = "weekDay")
    @Json(name = "weekDay")
    var weekDay:String?,

    @ColumnInfo(name = "month_id")
    @Json(name = "month_id")
    var month_id:Int?,

    ):Parcelable