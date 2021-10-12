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
data class Hour(

    @PrimaryKey(autoGenerate = true)
    @Json(name = "id")
    var id:Int?,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    var title:String?,

    @ColumnInfo(name = "reserve")
    @Json(name = "reserve")
    var reserve:Boolean?

):Parcelable
