package com.utechia.domain.usecases

import android.net.Uri
import com.utechia.domain.model.UploadModel

interface UploadUseCase {

    suspend fun execute(uri: Uri):UploadModel
}