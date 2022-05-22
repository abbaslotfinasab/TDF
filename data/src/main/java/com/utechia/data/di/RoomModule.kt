package com.utechia.data.di

import android.content.Context
import androidx.room.Room
import com.utechia.data.db.AppDataBase
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
    fun provideAppDatabase(@ApplicationContext context:Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java, "AppDatabase")
            .fallbackToDestructiveMigration()
            .build()

    }

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDataBase) = db.refreshmentDao()

    @Singleton
    @Provides
    fun provideRoomDao(db: AppDataBase) = db.roomDao()

}