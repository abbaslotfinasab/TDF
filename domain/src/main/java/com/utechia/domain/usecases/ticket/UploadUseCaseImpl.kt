package com.utechia.domain.usecases.ticket

import android.net.Uri
import com.utechia.domain.model.ticket.UploadModel
import com.utechia.domain.repository.ticket.UploadRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadUseCaseImpl @Inject constructor(private val uploadRepo: UploadRepo): UploadUseCase {

    override suspend fun execute(uri: Uri): UploadModel {
        return uploadRepo.upload(uri)
    }
}