package com.utechia.tdf.ticket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.utechia.data.entity.Ticket
import com.utechia.tdf.R
import java.text.SimpleDateFormat
import java.util.*

class TicketAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                .inflate(R.layout.ticket_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = ticket.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val rate: AppCompatButton = itemView.findViewById(R.id.btnRate)
        private val result: AppCompatButton = itemView.findViewById(R.id.btnResult)
        private val number: TextView = itemView.findViewById(R.id.status)
        private val startDate: TextView = itemView.findViewById(R.id.startDate)
        private val endDate: TextView = itemView.findViewById(R.id.endDate)

        fun bind0(position: Int) {


        }
    }
}


