package com.utechia.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utechia.data.entity.reservation.RoomData


@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(room: MutableList<RoomData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(room: RoomData)

    @Query("SELECT * FROM RoomData WHERE name=:room")
    suspend fun getALL(room:String):MutableList<RoomData>

    @Query("SELECT * FROM RoomData WHERE name LIKE '%' || :title ||'%'")
    suspend fun search(title:String):MutableList<RoomData>

    @Query("DELETE FROM RoomData")
    suspend fun deleteAll()

}