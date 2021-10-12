package com.utechia.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.utechia.data.entity.Day

@Dao
interface DayDao {

    @Query("SELECT * FROM Day WHERE month_id = :month_id")
    suspend fun getAll(month_id:Int): MutableList<Day>

}