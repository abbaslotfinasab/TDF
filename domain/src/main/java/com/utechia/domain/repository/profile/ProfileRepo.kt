package com.utechia.domain.repository.profile

import com.utechia.domain.model.profile.ProfileModel

interface ProfileRepo {
    suspend fun getProfile(): ProfileModel
}