package com.utechia.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utechia.data.entity.Reservation
import com.utechia.data.entity.Room

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRoom(room: Room)

    @Query("SELECT * FROM Room WHERE day_id=:day_id AND month_id=:month_id")
    suspend fun getAll(day_id:Int,month_id:Int): MutableList<Room>

}