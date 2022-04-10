package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.TicketModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TicketData(

    val id: Int?,
    val title: String?,
    val Priority: String?,
    val status: String?,
    val fid: String?,
    val description: String?,
    val mediaurl: List<String>?,
    val datetime: String?,
    val dateupdate: String?,
    val rateable: Boolean?,
    val category: Category,
    val department: String?,
    val requestername: String?,
    val adminname: String?,



    ):Parcelable, ResponseObject<TicketModel> {
    override fun toDomain(): TicketModel {
        return TicketModel(
            id,title, Priority, status, fid, description , mediaurl , datetime , dateupdate , rateable, category.toDomain(), department, requestername, adminname
        )
    }
}
