package com.utechia.tdf.ticket

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utechia.data.entity.Chat
import com.utechia.tdf.R
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private val ticketDetailsFragment: TicketDetailsFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var firebaseUploadAdapter:FirebaseUploadAdapter
    var chat: MutableList<Chat> = mutableListOf()
    var single = false
    var rateable = true
    var title = ""
    var mID = ""
    var category = ""
    var priority = ""
    var statusCode = ""

    var sdf: SimpleDateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private lateinit var dateFormat: Date
    private var simple = ""

    fun addData(_chat: Chat) {
        chat.add(_chat)
        notifyItemChanged(chat.size - 1)

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
            description.text = title
            user.text = category
            date.text = priority
            subTitle.text = mID

            if (statusCode == "Close"){
                status.visibility = View.VISIBLE
            }else{
                status.visibility = View.GONE
            }

            when (priority) {

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

            dateFormat = sdf.parse(chat[position].datetime)
            simple = SimpleDateFormat("yyyy.MM.dd | HH:mm", Locale.getDefault()).format(dateFormat)
            date.text = "$simple"

            description.text = chat[position].text
            user.text = chat[position].username

            data.apply {
                firebaseUploadAdapter = FirebaseUploadAdapter(chat[position].mediaurl!!)
                adapter = firebaseUploadAdapter
                layoutManager = GridLayoutManager(context, calculateNoOfColumns(context, 100.0F))
            }
            if (statusCode == "Close" && !single && rateable) {
                single = true
                ticketDetailsFragment.rating()
            }
        }
        private fun calculateNoOfColumns(
            context: Context,
            columnWidthDp: Float
        ): Int { // For example columnWidthdp=180
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            return (screenWidthDp / columnWidthDp + 0.5).toInt() // +0.5 for correct rounding to int.
        }
    }

    inner class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val description: TextView = itemView.findViewById(R.id.description)
        private val user: TextView = itemView.findViewById(R.id.username)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val data: RecyclerView = itemView.findViewById(R.id.recyclerView)

        fun bind0(position: Int) {

            dateFormat = sdf.parse(chat[position].datetime!!)!!
            simple = SimpleDateFormat("yyyy-MM-dd | HH:mm", Locale.getDefault()).format(dateFormat)
            date.text = "$simple"

            user.text = chat[position].username
            description.text = chat[position].text

            data.apply {
                firebaseUploadAdapter = FirebaseUploadAdapter(chat[position].mediaurl!!)
                adapter = firebaseUploadAdapter
                layoutManager = GridLayoutManager(context, calculateNoOfColumns(context, 100.0F))
            }

            if (statusCode == "Close" && !single && rateable) {
                single = true
                ticketDetailsFragment.rating()
            }
        }
    }

        private fun calculateNoOfColumns(
            context: Context,
            columnWidthDp: Float
        ): Int { // For example columnWidthdp=180
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            return (screenWidthDp / columnWidthDp + 0.5).toInt() // +0.5 for correct rounding to int.
    }
}


