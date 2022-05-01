package com.utechia.tdf.ticket

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.enum.TicketEnum
import com.utechia.domain.model.ticket.TicketModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TicketAdapter: PagingDataAdapter<TicketModel, TicketAdapter.MyViewHolder>(
    DiffUtilCallBack()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ticket_item, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)
        private val subTitle: TextView = itemView.findViewById(R.id.subTitle)
        private val category: TextView = itemView.findViewById(R.id.category)
        private val priority: TextView = itemView.findViewById(R.id.priority)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.ticket)
        private var timeZone = ""



        fun bind(ticket:TicketModel) {

            layout.setOnClickListener {
                val bundle = bundleOf("fid" to ticket.fid , "ticketId" to ticket.id)
                Log.d("ticketId",ticket.id.toString())
                itemView.findNavController().clearBackStack(R.id.ticketSystemFragment)
                itemView.findNavController().navigate(R.id.action_ticketSystemFragment_to_ticketDetailsFragment,bundle)

            }

            timeZone = OffsetDateTime.parse(ticket.dateupdate?:"2010-01-01T12:00:00+0100").atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            date.text = timeZone

            title.text = ticket.title
            subTitle.text = ticket.id.toString()
            category.text = ticket.category.title


            when (ticket.Priority) {

                TicketEnum.High.ticket -> {
                    priority.apply {
                        visibility = View.VISIBLE
                        text = itemView.context.getText(R.string.High)
                        setTextColor(ContextCompat.getColor(itemView.context,R.color.high))
                        background = ContextCompat.getDrawable(context,R.drawable.back_low)

                    }
                }

                TicketEnum.Low.ticket -> {
                    priority.apply {
                        visibility = View.VISIBLE
                        text = itemView.context.getText(R.string.Low)
                        setTextColor(ContextCompat.getColor(itemView.context,R.color.low))
                        background = ContextCompat.getDrawable(context,R.drawable.supporter_back)
                    }
                }

                else -> {

                    priority.apply {
                        visibility = View.VISIBLE
                        text = itemView.context.getText(R.string.Medium)
                        setTextColor(ContextCompat.getColor(itemView.context,R.color.medium))
                        background = ContextCompat.getDrawable(context,R.drawable.back_medium)

                    }
                }
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<TicketModel>() {
        override fun areItemsTheSame(
            oldItem: TicketModel,
            newItem: TicketModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TicketModel,
            newItem: TicketModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}


