package com.utechia.domain.usecases.ticket

import android.net.Uri
import com.utechia.domain.model.ticket.UploadModel

interface UploadUseCase {

    suspend fun execute(uri: Uri): UploadModel
}