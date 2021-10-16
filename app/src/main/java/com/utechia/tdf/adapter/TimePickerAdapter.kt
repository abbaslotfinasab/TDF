package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utechia.tdf.R

class TimePickerAdapter( private val timeList:MutableList<String>, private val createReservationAdapter: CreateReservationAdapter,var columnPosition: Int):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

            var clicked = false
            timeLayout.setOnClickListener{

                if (!clicked){

                    if (createReservationAdapter.lastPosition == columnPosition || createReservationAdapter.count ==0){
                        it.background =
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_timeselected)
                        createReservationAdapter.selectedTime.add(position.toFloat())
                        createReservationAdapter.selectedRoom.add(columnPosition)
                        clicked=true
                        createReservationAdapter.lastPosition = columnPosition
                        createReservationAdapter.count +=1
                    }
                    else{

                        Toast.makeText(itemView.context,"Please select one room",Toast.LENGTH_SHORT).show()
                    }

                }

                else {
                    clicked = false
                    createReservationAdapter.count -=1
                    createReservationAdapter.selectedTime.remove(position.toFloat())
                    if (createReservationAdapter.selectedTime.size==0)
                        createReservationAdapter.selectedRoom.remove(columnPosition)
                    it.background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_time)

                }

            }

        }

    }

}