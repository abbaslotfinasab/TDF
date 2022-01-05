package com.utechia.domain.repository

import android.net.Uri
import com.utechia.domain.model.UploadModel
import java.net.URI

interface UploadRepo {

    suspend fun upload(uri: Uri):UploadModel
}