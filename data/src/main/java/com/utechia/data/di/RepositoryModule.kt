package com.utechia.data.di

import com.utechia.data.repo.*
import com.utechia.domain.repository.*
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
    abstract fun bindLoginRepositoryService(
        loginRepoImpl: LoginRepoImpl
    ): LoginRepo

    @Binds
    abstract fun bindVerifyRepositoryService(
        verifyRepoImpl: VerifyRepoImpl
    ): VerifyRepo

    @Binds
    abstract fun bindRefreshmentRepositoryService(
        refreshmentRepoImpl: RefreshmentRepoImpl
    ): RefreshmentRepo


    @Binds
    abstract fun bindFavoriteRepositoryService(
        favoriteRepoImpl: FavoriteRepoImpl
    ): FavoriteRepo

    @Binds
    abstract fun bindCartsRepositoryService(
        cartRepoImpl: CartRepoImpl
    ): CartRepo

    @Binds
    abstract fun bindOrderRepositoryService(
        orderRepoImpl: OrderRepoImpl
    ): OrderRepo

    @Binds
    abstract fun bindOrderCountRepositoryService(
        orderRepoImpl: OrderCountRepoImpl
    ): OrderCountRepo

    @Binds
    abstract fun bindPermissionRepositoryService(
        permissionRepoImpl: PermissionRepoImpl
    ): PermissionRepo

    @Binds
    abstract fun bindPermissionTypeRepositoryService(
        permissionRepoImpl: PermissionTypeRepoImpl
    ): PermissionTypeRepo

    @Binds
    abstract fun bindCheckOutRepositoryService(
        checkOutRepoImpl: CheckOutRepoImpl
    ): CheckOutRepo

}