package com.utechia.tdf.reservation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.reservation.TimeModel
import com.utechia.tdf.R


class ReservationTimeAdapter(private val createReservationFragment: CreateReservationFragment):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val timeList: MutableList<TimeModel> = mutableListOf()
    var previousIndex = -1


    fun addData(_timeList: MutableList<TimeModel>) {
        timeList.clear()
        notifyDataSetChanged()
        timeList.addAll(_timeList)
        notifyItemRangeChanged(0, timeList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_time, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolder).bind0(holder.adapterPosition)

    }

    override fun getItemCount(): Int = timeList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeTitle:TextView = itemView.findViewById(R.id.timeline)
        private val timeLayout: ConstraintLayout = itemView.findViewById(R.id.timeLayout)

        fun bind0(position: Int) {

            timeTitle.text = timeList[position].time

            if (timeList[position].reserved==true){
                timeLayout.background = ContextCompat.getDrawable(itemView.context,R.drawable.reserved_back)
                timeTitle.setTextColor(ContextCompat.getColor(itemView.context,R.color.reserved_title))
                timeLayout.isEnabled = false

            }else{
                timeLayout.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.background))
                timeTitle.setTextColor(ContextCompat.getColor(itemView.context,R.color.black))
            }

            timeLayout.setOnClickListener {
                timeLayout.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.btnCalendarBack))
                timeTitle.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
                previousIndex = position
            }
        }
    }
}