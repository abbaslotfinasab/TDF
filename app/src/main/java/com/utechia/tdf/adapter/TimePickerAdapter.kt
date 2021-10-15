package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.moodel.RoomModel
import com.utechia.tdf.R

class TimePickerAdapter( private val timeList:MutableList<String>,private val roomModel: RoomModel, private val createReservationAdapter: CreateReservationAdapter):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder( LayoutInflater.from(parent.context)
        .inflate(R.layout.item_time, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolder).bind0(position)

    }

    override fun getItemCount(): Int = timeList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeTitle:TextView = itemView.findViewById(R.id.timeline)
        private val timeLayout:ConstraintLayout = itemView.findViewById(R.id.timeSelect)

        fun bind0(position: Int) {

            if (position%2==0)
            timeTitle.text = timeList[position]

            var count = -1
            timeLayout.setOnClickListener{
                count += 1

                if (count % 2 == 0){

                    createReservationAdapter.selectedRoom.add(roomModel.id!!)

                    createReservationAdapter.selectedTime.add(position.toFloat())

                    it.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.ic_timeselected)
                }

                else {
                    createReservationAdapter.selectedRoom.remove(roomModel.id)

                    it.background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_time)

                }

            }

        }

    }

}