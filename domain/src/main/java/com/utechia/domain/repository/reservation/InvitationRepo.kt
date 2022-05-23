package com.utechia.domain.repository.reservation

import com.utechia.domain.model.profile.ProfileModel


interface InvitationRepo {
    suspend fun getUserList(query: String):MutableList<ProfileModel>
}