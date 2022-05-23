package com.utechia.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utechia.data.entity.profile.ProfileData


@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(category: MutableList<ProfileData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: ProfileData)

    @Query("SELECT * FROM ProfileData")
    suspend fun getAll():MutableList<ProfileData>

    @Query("UPDATE ProfileData SET added =:status  WHERE id=:id")
    suspend fun add(id:Int,status:Boolean)

    @Query("SELECT * FROM ProfileData WHERE displayName LIKE '%' || :title ||'%'")
    suspend fun search(title:String):MutableList<ProfileData>

    @Query("DELETE FROM ProfileData")
    suspend fun deleteAll()

}