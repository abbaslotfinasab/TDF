package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.moodel.ReservationModel
import com.utechia.tdf.R

class BookedAdapter(private val bookList:MutableList<ReservationModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_booked, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolder).bind0(position)

    }

    override fun getItemCount(): Int = bookList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val roomTitle: TextView = itemView.findViewById(R.id.roomTitle)
        private val roomMember: TextView = itemView.findViewById(R.id.guestRoom)
        private val roomTime: TextView = itemView.findViewById(R.id.timeRoom)
        private val roomDuration: TextView = itemView.findViewById(R.id.durationRoom)
        private val roomDate: TextView = itemView.findViewById(R.id.dateRoom)
        private val more: AppCompatButton = itemView.findViewById(R.id.more)

        fun bind0(position: Int) {

            roomTitle.text = bookList[position].title
            roomTime.text = bookList[position].time
            roomDuration.text = bookList[position].duration
            roomDate.text = bookList[position].date

            more.setOnClickListener {

                /*   bookList[position].id
                bookList[position].room_id*/
            }

        }
    }
}