package com.utechia.data.repo

import android.util.Log
import com.utechia.data.api.Service
import com.utechia.data.entity.ReplyBody
import com.utechia.data.entity.TicketBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.TicketModel
import com.utechia.domain.repository.TicketRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper,

    ):TicketRepo {
    override suspend fun getAllTicket(status:String): MutableList<TicketModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getTicketList(status)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data!!.list?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun postTicket(
        description: String,
        title: String,
        category: Int,
        Priority: String,
        Floor: String,
        mediaurl: MutableSet<String>
    ): MutableList<TicketModel> {

        if (networkHelper.isNetworkConnected()) {

            Log.d("postChat",  TicketBody(
                Priority,category,description,Floor,mediaurl,title
            ).toString())
            val result = service.postTicket(
                TicketBody(
                    Priority,category,description,Floor,mediaurl,title
                )

            )

            return when (result.isSuccessful) {

                true -> {
                    emptyList<TicketModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun closeTicket(fid: Int): MutableList<TicketModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.closeTicket(fid)


            return when (result.isSuccessful) {

                true -> {
                    emptyList<TicketModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun replyTicket(
        id: Int,
        mediaurl: MutableSet<String>,
        text: String
    ): MutableList<TicketModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.reply(
                ReplyBody(
                    id,
                    mediaurl,
                    text
                )
            )

            return when (result.isSuccessful) {

                true -> {
                    emptyList<TicketModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}