package com.utechia.domain.repository.ticket

import android.net.Uri
import com.utechia.domain.model.ticket.UploadModel

interface UploadRepo {

    suspend fun upload(uri: Uri): UploadModel
}