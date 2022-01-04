package com.utechia.tdf.ticket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utechia.data.entity.Chat
import com.utechia.tdf.R
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var chat: MutableList<Chat> = mutableListOf()

    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private lateinit var dateFormat: Date
    private var simple = ""

    fun addData(_chat: Chat) {

        chat.add(_chat)
        notifyItemChanged(chat.size-1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (chat[viewType].UserRol){
            "Requester" -> {
                ViewHolder0(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_ticket_client, parent, false)
                )
            }
                else -> {
                    ViewHolder1(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_chat_admin, parent, false)
                    )
                }
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (chat[position].UserRol) {
            "Requester" -> {
                (holder as ViewHolder0).bind0(position)
            }
            else -> {
                (holder as ViewHolder1).bind0(position)

            }
        }
    }

    override fun getItemCount(): Int = chat.size

    override fun getItemViewType(position: Int): Int = position


    inner class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val description: TextView = itemView.findViewById(R.id.description)
        private val user: TextView = itemView.findViewById(R.id.username)
        private val date: TextView = itemView.findViewById(R.id.date)


        fun bind0(position: Int) {

            dateFormat = sdf.parse(chat[position].datetime)
            simple = SimpleDateFormat("yyyy.MM.dd | HH:mm", Locale.getDefault()).format(dateFormat)
            date.text = "$simple"

            description.text = chat[position].text
            user.text = chat[position].username




        }
    }

    inner class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val description: TextView = itemView.findViewById(R.id.description)
        private val user: TextView = itemView.findViewById(R.id.username)
        private val date: TextView = itemView.findViewById(R.id.date)


        fun bind0(position: Int) {

            dateFormat = sdf.parse(chat[position].datetime)
            simple = SimpleDateFormat("yyyy-MM-dd | HH:mm", Locale.getDefault()).format(dateFormat)
            date.text = "$simple"

            description.text = chat[position].text
            user.text = chat[position].username




        }
    }
}


