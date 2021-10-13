package com.utechia.data.di

import android.content.Context
import androidx.room.Room
import com.utechia.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context:Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "AppDatabase")
            .build()

    }

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDatabase) = db.roomDao()

    @Singleton
    @Provides
    fun provideBandDao(db: AppDatabase) = db.reservationDao()

}
