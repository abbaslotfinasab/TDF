package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utechia.tdf.R
import java.text.SimpleDateFormat
import java.util.*

class TimePickerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val timeList:MutableList<String> = mutableListOf()
    private val sdf:SimpleDateFormat = SimpleDateFormat("HH:mm")
    private val calendar:Calendar = Calendar.getInstance()

    init {
        getTime()
    }

    private fun getTime(){
        timeList.clear()
        for(i in 0 until 24){
            calendar.set(Calendar.HOUR_OF_DAY,i)
            calendar.set(Calendar.MINUTE,0)
            timeList.add(sdf.format(calendar.time))
            calendar.set(Calendar.HOUR_OF_DAY,i)
            calendar.set(Calendar.MINUTE,30)
            timeList.add(sdf.format(calendar.time))
        }

    }


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

            var count = 0
            timeLayout.setOnClickListener{
                count += 1
                if (count %2 == 1)
                it.background = ContextCompat.getDrawable(itemView.context,R.drawable.ic_timeselected)
                else
                    it.background = ContextCompat.getDrawable(itemView.context,R.drawable.ic_time)

            }
        }

    }

}