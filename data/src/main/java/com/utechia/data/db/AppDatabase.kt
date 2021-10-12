package com.utechia.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.utechia.data.dao.*
import com.utechia.data.entity.*


@Database(entities = [
    Room::class,
    Day::class,
    Reservation::class,],version = 1,exportSchema = false)

abstract class AppDatabase: RoomDatabase() {

    abstract fun roomDao():RoomDao
    abstract fun reservationDao():ReservationDao
    abstract fun dayDao():DayDao

}