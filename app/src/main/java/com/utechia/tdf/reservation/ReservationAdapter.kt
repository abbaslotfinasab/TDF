package com.utechia.tdf.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.enum.ReservationEnum
import com.utechia.domain.model.reservation.MeetingModel
import com.utechia.tdf.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ReservationAdapter: PagingDataAdapter<MeetingModel, ReservationAdapter.MyViewHolder>(
    DiffUtilCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_reservation, parent, false)
        )

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTitle: TextView = itemView.findViewById(R.id.dayTitle)
        private val dayNumber: TextView = itemView.findViewById(R.id.dayNumber)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val dayOfWeek: TextView = itemView.findViewById(R.id.dayOfWeek)
        private val dateTime: TextView = itemView.findViewById(R.id.dateTime)
        private val floor: TextView = itemView.findViewById(R.id.floor)
        private val cancel: TextView = itemView.findViewById(R.id.btnCancel)


        fun bind(meetingModel: MeetingModel) {
            dayTitle.text = LocalDate.parse(meetingModel.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).month.name
            dayNumber.text = LocalDate.parse(meetingModel.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).dayOfMonth.toString()
            floor.text = "Floor ${meetingModel.room?.floor?.name}"
            title.text = meetingModel.subject
            dayOfWeek.text = "${LocalDate.parse(meetingModel.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }}"
            dateTime.text = ", ${LocalTime.parse(meetingModel.startsAt,DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("HH:mm"))} to ${LocalTime.parse(meetingModel.endsAt,DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("HH:mm"))} (${meetingModel.duration}M)"

            if (meetingModel.status == ReservationEnum.Cancel.reservation){
                cancel.visibility = View.VISIBLE
            }else{
                cancel.visibility = View.INVISIBLE
            }



        }
    }
    class DiffUtilCallBack : DiffUtil.ItemCallback<MeetingModel>() {
        override fun areItemsTheSame(
            oldItem: MeetingModel,
            newItem: MeetingModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MeetingModel,
            newItem: MeetingModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}


