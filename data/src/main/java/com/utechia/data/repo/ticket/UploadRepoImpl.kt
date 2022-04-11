package com.utechia.data.repo.ticket

import android.net.Uri
import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.ticket.UploadModel
import com.utechia.domain.repository.ticket.UploadRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UploadRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper,

    ): UploadRepo {
    override suspend fun upload(uri: Uri): UploadModel {

        val file = File(uri.path!!)
        val body = MultipartBody.Part.createFormData("file",file.name,file.asRequestBody())

        if (networkHelper.isNetworkConnected()) {

            val result = service.uploadFile(body)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data!!.toDomain()
                }

                else -> {
                    throw IOException("Server is Not Responding")
                }
            }
        } else throw IOException("No Internet Connection")
    }
}