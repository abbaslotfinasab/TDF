package com.utechia.tdf.ticket

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.TicketModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TicketAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ticket: MutableList<TicketModel> = mutableListOf()
    private var timeZone = ""


    fun addData(_ticket: MutableList<TicketModel>) {
        ticket.clear()
        ticket.addAll(_ticket)
        notifyDataSetChanged()

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
        private val subTitle: TextView = itemView.findViewById(R.id.subTitle)
        private val category: TextView = itemView.findViewById(R.id.category)
        private val priority: TextView = itemView.findViewById(R.id.priority)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.ticket)


        fun bind0(position: Int) {

            layout.setOnClickListener {
                val bundle = bundleOf("fid" to ticket[position].fid , "ticketId" to ticket[position].id)
                itemView.findNavController().clearBackStack(R.id.ticketSystemFragment)
                itemView.findNavController().navigate(R.id.action_ticketSystemFragment_to_ticketDetailsFragment,bundle)

            }

            timeZone = OffsetDateTime.parse(ticket[position].dateupdate).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            date.text = "$timeZone"

            title.text = ticket[position].title
            subTitle.text = ticket[position].id.toString()
            category.text = ticket[position].category.title


            when (ticket[position].Priority) {

                "High" -> {
                    priority.apply {
                        visibility = View.VISIBLE
                        text = "High"
                        setTextColor(Color.parseColor("#FF6464"))
                        background = ContextCompat.getDrawable(context,R.drawable.back_low)

                    }
                }

                "Low" -> {
                    priority.apply {
                        visibility = View.VISIBLE
                        text = "Low"
                        setTextColor(Color.parseColor("#59B48D"))
                        background = ContextCompat.getDrawable(context,R.drawable.supporter_back)
                    }
                }

                else -> {

                    priority.apply {
                        visibility = View.VISIBLE
                        text = "Medium"
                        setTextColor(Color.parseColor("#F5A62E"))
                        background = ContextCompat.getDrawable(context,R.drawable.back_medium)

                    }
                }
            }
        }
    }
}


