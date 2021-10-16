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

    @ColumnInfo(name = "capacity")
    @Json(name = "capacity")
    var capacity:Int?,

    @ColumnInfo(name = "roomImage")
    @Json(name = "roomImage")
    var roomImage:String?,

    @ColumnInfo(name = "room_id")
    @Json(name = "room_id")
    var room_id:Int?,

    @ColumnInfo(name = "day")
    @Json(name = "day")
    var day:String?,

    @ColumnInfo(name = "month")
    @Json(name = "month")
    var month:String?,

    @ColumnInfo(name = "year")
    @Json(name = "year")
    var year:String?,


    @ColumnInfo(name = "time")
    @Json(name = "time")
    var time:String?,

    @ColumnInfo(name = "guest")
    @Json(name = "guest")
    var guest:Int?,

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
