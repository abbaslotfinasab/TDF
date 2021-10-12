package com.utechia.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
@Entity
data class Room(

    @PrimaryKey(autoGenerate = true)
    @Json(name = "id")
    var id:Int?,

    @ColumnInfo(name = "name")
    @Json(name = "name")
    var name:String?,

    @ColumnInfo(name = "capacity")
    @Json(name = "capacity")
    var capacity:Int?,

    @ColumnInfo(name = "image")
    @Json(name = "image")
    var image:String?,

    @ColumnInfo(name = "day_id")
    @Json(name = "day_id")
    var day_id:Int?,

    @ColumnInfo(name = "month_id")
    @Json(name = "month_id")
    var month_id:Int?,

  /*  @Embedded
    @Json(name = "hour")
    var hour: MutableList<Hour>?,*/

):Parcelable
