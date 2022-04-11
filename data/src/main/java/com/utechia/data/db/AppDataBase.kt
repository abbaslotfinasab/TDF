package com.utechia.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.utechia.data.dao.RefreshmentDao
import com.utechia.data.entity.refreshment.RefreshmentData


@Database(entities = [
    RefreshmentData::class
                     ],version = 1)

abstract class AppDataBase: RoomDatabase() {

    abstract fun refreshmentDao(): RefreshmentDao

}