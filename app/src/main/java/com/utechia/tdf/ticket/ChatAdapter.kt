package com.utechia.tdf.ticket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utechia.data.entity.Ticket
import com.utechia.tdf.R
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ticket: MutableList<Ticket> = mutableListOf()

    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private lateinit var dateFormat: Date
    private var simple = ""

    fun addData(_ticket: Ticket) {

        ticket.add(_ticket)
        notifyItemChanged(ticket.size-1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_survey, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = ticket.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind0(position: Int) {


        }
    }
}


