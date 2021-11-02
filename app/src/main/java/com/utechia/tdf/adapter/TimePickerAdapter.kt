package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.graphics.Color.parseColor
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.HourModel
import com.utechia.tdf.R
import com.utechia.tdf.fragment.CreateReservationFragment

class TimePickerAdapter(private val createReservationFragment: CreateReservationFragment):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun getItemCount(): Int = timeList.size-1


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title:TextView = itemView.findViewById(R.id.timeline)
        private val back:ConstraintLayout = itemView.findViewById(R.id.timeLayout)


        fun bind0(position: Int) {

            var clicked = false

            title.text = timeList[position].title

            back.setBackgroundColor(parseColor("#96AAF0"))

            if (!timeList[position].available){
                back.apply {
                    setBackgroundColor(parseColor("#E9E9E9"))
                    isEnabled = false
                }

            }
            else {
                back.setOnClickListener {

                    clicked = if (!clicked) {
                        it.setBackgroundColor(parseColor("#30B68B"))

                        createReservationFragment.selected.add(timeList[position].title)
                        createReservationFragment.selected.add(timeList[position+1].title)
                        createReservationFragment.setTime()

                        true

                    } else {

                        it.setBackgroundColor(parseColor("#96AAF0"))

                        if (createReservationFragment.selected.size<3)
                            createReservationFragment.selected.clear()
                        else
                            createReservationFragment.selected.remove(timeList[position+1].title)

                        createReservationFragment.setTime()

                        false
                    }
                }
            }
        }
    }
}