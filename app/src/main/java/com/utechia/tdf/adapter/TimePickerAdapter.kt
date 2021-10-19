package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.HourModel
import com.utechia.tdf.R

class TimePickerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val timeList:MutableList<HourModel> = mutableListOf()


    fun addData(data:MutableList<HourModel>){

        timeList.clear()
        timeList.addAll(data)
        notifyItemRangeChanged(0,timeList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder( LayoutInflater.from(parent.context)
        .inflate(R.layout.item_time, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolder).bind0(position)

    }

    override fun getItemCount(): Int = timeList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title:TextView = itemView.findViewById(R.id.timeline)
        private val back:ConstraintLayout = itemView.findViewById(R.id.timeLayout)


        fun bind0(position: Int) {

            var count = -1
            title.text = timeList[position].title

            if (!timeList[position].available){
                back.apply {
                    setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.black))
                    isEnabled = false
                }

            }
            else {

                back.setOnClickListener {

                    count +=1

                    if (count % 2 ==0)
                    it.setBackgroundColor(android.graphics.Color.parseColor("#30B68B"))
                    else
                        it.setBackgroundColor(android.graphics.Color.parseColor("#96AAF0"))

                }
            }
        }
    }
}