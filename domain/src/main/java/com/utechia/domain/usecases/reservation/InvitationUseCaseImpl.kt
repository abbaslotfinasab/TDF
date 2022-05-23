package com.utechia.domain.usecases.reservation

import com.utechia.domain.model.profile.ProfileModel
import com.utechia.domain.repository.reservation.InvitationRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvitationUseCaseImpl @Inject constructor(private val invitationRepo: InvitationRepo):InvitationUseCase<ProfileModel> {
    override suspend fun execute(query: String): MutableList<ProfileModel> {
        return invitationRepo.getUserList(query)
    }
}