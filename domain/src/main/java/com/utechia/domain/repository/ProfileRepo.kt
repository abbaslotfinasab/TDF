package com.utechia.domain.repository

import com.utechia.domain.model.ProfileModel

interface ProfileRepo {
    suspend fun getProfile():ProfileModel
}