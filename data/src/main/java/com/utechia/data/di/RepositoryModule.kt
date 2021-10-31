package com.utechia.data.di

import com.utechia.data.repo.ReservationRepoImpl
import com.utechia.data.repo.RoomRepoImpl
import com.utechia.data.repo.SearchRepoImpl
import com.utechia.data.repo.TeaBoyRepoImpl
import com.utechia.domain.repository.ReservationRepo
import com.utechia.domain.repository.RoomRepo
import com.utechia.domain.repository.SearchRepo
import com.utechia.domain.repository.TeaBoyRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCategoryRepositoryService(
        roomRepoImpl: RoomRepoImpl
    ): RoomRepo

    @Binds
    abstract fun bindContentRepositoryService(
        reservationRepoImpl: ReservationRepoImpl
    ): ReservationRepo

    @Binds
    abstract fun bindRefreshmentRepositoryService(
        teaBoyRepoImpl: TeaBoyRepoImpl
    ): TeaBoyRepo

    @Binds
    abstract fun bindSearchRepositoryService(
        searchRepoImpl: SearchRepoImpl
    ): SearchRepo

}