package com.utechia.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.utechia.data.dao.ProfileDao
import com.utechia.data.dao.RefreshmentDao
import com.utechia.data.dao.RoomDao
import com.utechia.data.entity.profile.ProfileData
import com.utechia.data.entity.refreshment.RefreshmentData
import com.utechia.data.entity.reservation.RoomData


@Database(entities = [
    RefreshmentData::class,
    RoomData::class,
    ProfileData::class],version = 3)

abstract class AppDataBase: RoomDatabase() {

    abstract fun refreshmentDao(): RefreshmentDao
    abstract fun roomDao(): RoomDao
    abstract fun profileDao(): ProfileDao

}