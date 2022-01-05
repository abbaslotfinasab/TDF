package com.utechia.domain.usecases

import android.net.Uri
import com.utechia.domain.model.UploadModel
import com.utechia.domain.repository.UploadRepo
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadUseCaseImpl @Inject constructor(private val uploadRepo: UploadRepo):UploadUseCase {

    override suspend fun execute(uri: Uri): UploadModel {
        return uploadRepo.upload(uri)
    }
}