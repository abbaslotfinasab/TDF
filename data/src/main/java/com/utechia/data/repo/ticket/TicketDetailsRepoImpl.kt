package com.utechia.data.repo.ticket

import com.utechia.data.api.Service
import com.utechia.data.entity.ticket.RateTicketBody
import com.utechia.data.entity.ticket.ReplyBody
import com.utechia.data.entity.ticket.TicketBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.ticket.TicketModel
import com.utechia.domain.repository.ticket.TicketDetailsRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketDetailsRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper,

    ): TicketDetailsRepo {

    override suspend fun getSingleTicket(id: Int): MutableList<TicketModel>  {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getTicket(id)

            return when (result.isSuccessful && result.body()?.data !=null) {

                true -> {
                    listOf(result.body()?.data?.toDomain()!!).toMutableList()
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
        Floor: Int,
        mediaurl: MutableSet<String>
    ): MutableList<TicketModel> {

        if (networkHelper.isNetworkConnected()) {

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

    override suspend fun rateTicket(id: Int, rate: Int): MutableList<TicketModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.rateTicket(
                RateTicketBody(
                    rate,
                    id
                )
            )

            return when (result.isSuccessful) {

                true -> {
                    emptyList<TicketModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")    }
}
