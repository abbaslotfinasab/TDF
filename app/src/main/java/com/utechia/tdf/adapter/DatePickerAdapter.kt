package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.moodel.DayModel
import com.utechia.tdf.R

class DatePickerAdapter(private val dayModel: MutableList<DayModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder( LayoutInflater.from(parent.context)
        .inflate(R.layout.item_date, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolder).bind0(position)

    }

    override fun getItemCount(): Int = dayModel.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val weekTitle: TextView = itemView.findViewById(R.id.weeKTitle)
        private val dayNumber: TextView = itemView.findViewById(R.id.dateNumber)
        private val timeLayout: ConstraintLayout = itemView.findViewById(R.id.backDate)

        fun bind0(position: Int) {

            weekTitle.text = dayModel[position].weekDay
            dayNumber.text = dayModel[position].id.toString()

            if (position % 2 == 0) {

                var count = 0
                timeLayout.setOnClickListener {
                    count += 1
                    if (count % 2 == 1)
                        it.background =
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_day_selected)
                    else
                        it.background =
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_day)
                }


            }

        }
    }

}