package com.utechia.tdf.ticket

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utechia.data.entity.Chat
import com.utechia.domain.model.TicketModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ChatAdapter(private val ticketDetailsFragment: TicketDetailsFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var firebaseUploadAdapter:FirebaseUploadAdapter

    var chat: MutableList<Chat> = mutableListOf()
    var ticket: MutableList<TicketModel> = mutableListOf()
    private var single = false
    private var timeZone = ""


    fun addData(_chat: Chat) {
        chat.add(_chat)
        notifyItemChanged(chat.size)

    }

    fun addDetails(_ticket:TicketModel) {
        ticket.clear()
        ticket.add(_ticket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 0)
            return ViewHolder0(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_details, parent, false)
            )
        else
            return when (chat[viewType-1].UserRol) {
                "Requester" -> {
                    ViewHolder1(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_chat_client, parent, false)
                    )
                }
                else -> {
                    ViewHolder2(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_chat_admin, parent, false)
                    )
                }
            }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position==0) {
            (holder as ViewHolder0).bind0(0)
        }
        else {
            return when (chat[position - 1].UserRol) {
                "Requester" -> {
                    (holder as ViewHolder1).bind0(position - 1)
                }
                else -> {
                    (holder as ViewHolder2).bind0(position - 1)
                }
            }
        }
    }

    override fun getItemCount(): Int = chat.size+1

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val description: TextView = itemView.findViewById(R.id.title)
        private val user: TextView = itemView.findViewById(R.id.category)
        private val date: TextView = itemView.findViewById(R.id.priority)
        private val subTitle: TextView = itemView.findViewById(R.id.subTitle)
        private val status: TextView = itemView.findViewById(R.id.status)

        fun bind0(position: Int) {
            description.text = ticket[position].description
            user.text = ticket[position].category.title
            date.text =  ticket[position].Priority
            subTitle.text = ticket[position].id.toString()

            if (ticket[position].status == "Close"){
                status.visibility = View.VISIBLE
            }else{
                status.visibility = View.GONE
            }

            when (ticket[position].Priority) {

                "High" -> {
                    date.apply {
                        visibility = View.VISIBLE
                        text = "High"
                        setTextColor(Color.parseColor("#FF6464"))

                    }
                }

                "Low" -> {
                    date.apply {
                        visibility = View.VISIBLE
                        text = "Low"
                        setTextColor(Color.parseColor("#59B48D"))
                    }
                }

                else -> {
                    date.apply {
                        visibility = View.VISIBLE
                        text = "Medium"
                        setTextColor(Color.parseColor("#F5A62E"))
                    }
                }
            }


        }
    }


    inner class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val description: TextView = itemView.findViewById(R.id.description)
        private val user: TextView = itemView.findViewById(R.id.username)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val data:RecyclerView = itemView.findViewById(R.id.recyclerView)



        fun bind0(position: Int) {

            timeZone = OffsetDateTime.parse(chat[position].datetime).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            date.text = "$timeZone"

            description.text = chat[position].text
            user.text = chat[position].username

            if (chat[position].mediaurl.isNullOrEmpty()){
                data.visibility = View.GONE
            }else {
                data.apply {
                    visibility = View.VISIBLE
                    firebaseUploadAdapter = FirebaseUploadAdapter(chat[position].mediaurl!!)
                    adapter = firebaseUploadAdapter
                    layoutManager = GridLayoutManager(context, calculateNoOfColumns(context))
                }
            }

            if (ticket[0].status == "Close" && !single && ticket[0].rateable!!) {
                single = true
                ticketDetailsFragment.rating(ticket[0].id!!)
            }
        }

        private fun calculateNoOfColumns(
            context: Context,
        ): Int { // For example columnWidthdp=180
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            return (screenWidthDp / 100 + 0.5).toInt() // +0.5 for correct rounding to int.
        }
    }

    inner class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val description: TextView = itemView.findViewById(R.id.description)
        private val user: TextView = itemView.findViewById(R.id.username)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val data: RecyclerView = itemView.findViewById(R.id.recyclerView)

        fun bind0(position: Int) {

            timeZone = OffsetDateTime.parse(chat[position].datetime).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            date.text = "$timeZone"

            user.text = chat[position].username
            description.text = chat[position].text

            if (chat[position].mediaurl.isNullOrEmpty()){
                data.visibility = View.GONE
            }else {
                data.apply {
                    visibility = View.VISIBLE
                    firebaseUploadAdapter = FirebaseUploadAdapter(chat[position].mediaurl!!)
                    adapter = firebaseUploadAdapter
                    layoutManager = GridLayoutManager(context, calculateNoOfColumns(context))
                }
            }

            if (ticket[0].status == "Close" && !single && ticket[0].rateable!!) {
                single = true
                ticketDetailsFragment.rating(ticket[0].id!!)
            }
        }
    }

        private fun calculateNoOfColumns(
            context: Context,
        ): Int { // For example columnWidthdp=180
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            return (screenWidthDp / 100 + 0.5).toInt() // +0.5 for correct rounding to int.
    }
}


