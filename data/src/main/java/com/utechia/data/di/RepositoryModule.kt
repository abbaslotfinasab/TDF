package com.utechia.data.di

import com.utechia.data.repo.cart.CartRepoImpl
import com.utechia.data.repo.cart.OfficeRepoImpl
import com.utechia.data.repo.event.EventDetailsRepoImpl
import com.utechia.data.repo.event.EventRepoImpl
import com.utechia.data.repo.favorite.FavoriteRepoImpl
import com.utechia.data.repo.health.TopStepsRepoImpl
import com.utechia.data.repo.home.TeaBoyActiveRepoImpl
import com.utechia.data.repo.login.LoginRepoImpl
import com.utechia.data.repo.login.VerifyRepoImpl
import com.utechia.data.repo.main.MainRepoImpl
import com.utechia.data.repo.notification.NotificationDetailsRepoImpl
import com.utechia.data.repo.notification.NotificationRepoImpl
import com.utechia.data.repo.order.teaboyorder.OrderCountRepoImpl
import com.utechia.data.repo.order.teaboyorder.TeaBoyOrderDetailsRepoImpl
import com.utechia.data.repo.permission.PermissionRepoImpl
import com.utechia.data.repo.permission.PermissionTypeRepoImpl
import com.utechia.data.repo.survey.SurveyRepoImpl
import com.utechia.data.repo.order.teaboyorder.TeaBoyOrderRepoImpl
import com.utechia.data.repo.ticket.BaseNeedsRepoImpl
import com.utechia.data.repo.ticket.TicketDetailsRepoImpl
import com.utechia.data.repo.ticket.TicketRepoImpl
import com.utechia.data.repo.order.userorder.UserOrderDetailsRepoImpl
import com.utechia.data.repo.order.userorder.UserOrderRepoImpl
import com.utechia.data.repo.permission.PermissionDetailsRepoImpl
import com.utechia.data.repo.profile.ProfileRepoImpl
import com.utechia.data.repo.refreshment.RefreshmentRepoImpl
import com.utechia.data.repo.reservation.*
import com.utechia.data.repo.survey.SurveyDetailsRepoImpl
import com.utechia.data.repo.ticket.UploadRepoImpl
import com.utechia.domain.repository.cart.CartRepo
import com.utechia.domain.repository.cart.OfficeRepo
import com.utechia.domain.repository.event.EventDetailRepo
import com.utechia.domain.repository.event.EventRepo
import com.utechia.domain.repository.favorite.FavoriteRepo
import com.utechia.domain.repository.health.TopStepsRepo
import com.utechia.domain.repository.home.TeaBoyActiveRepo
import com.utechia.domain.repository.login.LoginRepo
import com.utechia.domain.repository.main.MainRepo
import com.utechia.domain.repository.notification.NotificationDetailsRepo
import com.utechia.domain.repository.notification.NotificationRepo
import com.utechia.domain.repository.order.OrderCountRepo
import com.utechia.domain.repository.order.TeaBoyOrderDetailsRepo
import com.utechia.domain.repository.order.TeaBoyOrderRepo
import com.utechia.domain.repository.permission.PermissionDetailsRepo
import com.utechia.domain.repository.permission.PermissionRepo
import com.utechia.domain.repository.permission.PermissionTypeRepo
import com.utechia.domain.repository.profile.ProfileRepo
import com.utechia.domain.repository.profile.UserOrderDetailsRepo
import com.utechia.domain.repository.profile.UserOrderRepo
import com.utechia.domain.repository.refreshment.RefreshmentRepo
import com.utechia.domain.repository.reservation.*
import com.utechia.domain.repository.survey.SurveyDetailsRepo
import com.utechia.domain.repository.survey.SurveyRepo
import com.utechia.domain.repository.ticket.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRoomRepositoryService(
        roomRepoImpl: RoomRepoImpl
    ): RoomRepo

    @Binds
    abstract fun bindReservationRepositoryService(
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

    @Binds
    abstract fun bindProfileRepositoryService(
        profileRepoImpl: ProfileRepoImpl
    ): ProfileRepo

    @Binds
    abstract fun bindOfficeRepositoryService(
        officeRepoImpl: OfficeRepoImpl
    ): OfficeRepo

    @Binds
    abstract fun bindUserOrderDetailsService(
        userOrderDetailsRepoImpl: UserOrderDetailsRepoImpl
    ): UserOrderDetailsRepo

    @Binds
    abstract fun bindTeaBoyOrderDetailsService(
        teaBoyOrderDetailsRepoImpl: TeaBoyOrderDetailsRepoImpl
    ): TeaBoyOrderDetailsRepo

    @Binds
    abstract fun bindNotificationDetailsService(
        notificationDetailsRepoImpl: NotificationDetailsRepoImpl
    ): NotificationDetailsRepo

    @Binds
    abstract fun bindSurveyDetailsService(
        surveyDetailsRepoImpl: SurveyDetailsRepoImpl
    ): SurveyDetailsRepo

    @Binds
    abstract fun bindPermissionDetailsService(
        permissionDetailsRepoImpl: PermissionDetailsRepoImpl
    ): PermissionDetailsRepo

    @Binds
    abstract fun bindTeaBoyActiveService(
        teaBoyActiveRepoImpl: TeaBoyActiveRepoImpl
    ): TeaBoyActiveRepo

    @Binds
    abstract fun bindInvitationService(
        invitationRepo: InvitationRepoImpl
    ): InvitationRepo

    @Binds
    abstract fun bindMeetingTimeService(
        timeRepoImpl: TimeRepoImpl
    ): TimeRepo

    @Binds
    abstract fun bindMeetingService(
        meetingRepoImpl: MeetingRepoImpl
    ): MeetingRepo
}