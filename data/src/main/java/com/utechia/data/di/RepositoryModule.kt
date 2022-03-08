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
        orderRepoImpl: UserOrderRepoImpl
    ): UserOrderRepo

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
    abstract fun bindSurveyRepositoryService(
        surveyRepoImpl: SurveyRepoImpl
    ): SurveyRepo

    @Binds
    abstract fun bindTeaBoyOrderRepositoryService(
        teaBoyOrderRepoImpl: TeaBoyOrderRepoImpl
    ): TeaBoyOrderRepo

    @Binds
    abstract fun bindTicketRepositoryService(
        ticketRepoImpl: TicketRepoImpl
    ): TicketRepo

    @Binds
    abstract fun bindBaseNeedsRepositoryService(
        baseNeedsRepoImpl: BaseNeedsRepoImpl
    ): BaseNeedsRepo

    @Binds
    abstract fun bindUploadRepositoryService(
        uploadRepoImpl: UploadRepoImpl
    ): UploadRepo

    @Binds
    abstract fun bindNotificationRepositoryService(
        notificationRepoImpl: NotificationRepoImpl
    ): NotificationRepo

    @Binds
    abstract fun bindMainRepositoryService(
        mainRepoImpl: MainRepoImpl
    ): MainRepo

    @Binds
    abstract fun bindTicketDetailsRepositoryService(
        ticketDetailsRepoImpl: TicketDetailsRepoImpl
    ): TicketDetailsRepo

    @Binds
    abstract fun bindEventsRepositoryService(
        eventRepoImpl: EventRepoImpl
    ): EventRepo

    @Binds
    abstract fun bindEventsDetailsRepositoryService(
        eventDetailsRepoImpl: EventDetailsRepoImpl
    ): EventDetailRepo

    @Binds
    abstract fun bindTopStepsRepositoryService(
        topStepsRepoImpl: TopStepsRepoImpl
    ): TopStepsRepo
}