package com.utechia.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utechia.data.entity.refreshment.RefreshmentData


@Dao
interface RefreshmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(category: MutableList<RefreshmentData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: RefreshmentData)

    @Query("SELECT * FROM RefreshmentData")
    suspend fun getSize():MutableList<RefreshmentData>

    @Query("SELECT * FROM RefreshmentData WHERE category=:category")
    suspend fun getALL(category:String):MutableList<RefreshmentData>

    @Query("UPDATE RefreshmentData SET number = :number WHERE id=:id")
    suspend fun updateNumber(id:Int,number:Int)

    @Query("SELECT * FROM RefreshmentData WHERE title LIKE '%' || :title ||'%'")
    suspend fun search(title:String):MutableList<RefreshmentData>

    @Query("UPDATE RefreshmentData SET number = 0 WHERE id=:id")
    suspend fun deleteItem(id:Int)

    @Query("UPDATE RefreshmentData SET isFavorite = 1 WHERE id=:id")
    suspend fun like(id:Int)

    @Query("UPDATE RefreshmentData SET isFavorite =0 WHERE id=:id")
    suspend fun dislike(id:Int)

    @Query("DELETE FROM RefreshmentData")
    suspend fun deleteAll()

}