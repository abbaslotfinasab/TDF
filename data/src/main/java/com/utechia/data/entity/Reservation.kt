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
data class Reservation(

    @PrimaryKey(autoGenerate = true)
    @Json(name = "id")
    var id:Int?,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    var title:String?,

    @ColumnInfo(name = "room_id")
    @Json(name = "room_id")
    var room_id:Int?,

    @ColumnInfo(name = "date")
    @Json(name = "date")
    var date:String?,

    @ColumnInfo(name = "time")
    @Json(name = "time")
    var time:String?,

    @ColumnInfo(name = "duration")
    @Json(name = "duration")
    var duration:String?,

    @ColumnInfo(name = "description")
    @Json(name = "description")
    var description:String?,

   /* @ColumnInfo(name = "guess_id")
    @Json(name = "guess_id")
    var guess_id:MutableList<Int>,
*/

    ) : Parcelable
