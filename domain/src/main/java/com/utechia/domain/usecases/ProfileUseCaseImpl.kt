package com.utechia.domain.usecases

import com.utechia.domain.model.ProfileModel
import com.utechia.domain.repository.ProfileRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileUseCaseImpl @Inject constructor(private val profileRepo: ProfileRepo):ProfileUseCase<ProfileModel> {

    override suspend fun execute(): ProfileModel {
        return profileRepo.getProfile()
    }

}