package com.utechia.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utechia.data.entity.Reservation
import com.utechia.data.entity.Room

@Dao
interface ReservationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReservation(reservation: Reservation)

    @Query("SELECT * FROM Reservation ")
    suspend fun getAll(): MutableList<Reservation>

    @Query("SELECT * FROM Reservation WHERE id=:id")
    suspend fun get(id:Int): Reservation

}