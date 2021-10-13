package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utechia.tdf.R
import java.util.*
import kotlin.collections.ArrayList

class DatePickerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var calendar = Calendar.getInstance()
    private var day:ArrayList<Int> = arrayListOf()
    private var week:ArrayList<Int> = arrayListOf()
    private var selected = -1

    var currentMonth: Int = 0
    var currentDay: Int = 0



    fun addData(_currentMonth: Int) {
        day.clear()
        week.clear()
        currentMonth = _currentMonth
        calendar.set(Calendar.MONTH, _currentMonth)
        val s = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        val e = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in s..e) {
            calendar.set(Calendar.MONTH, _currentMonth)
            day.add(i)
            calendar.set(Calendar.DAY_OF_MONTH, i)
            week.add(calendar.get(Calendar.DAY_OF_WEEK))

        }

        notifyItemRangeChanged(0, e-1 )

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_date, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolder).bind0(position)

    }

    override fun getItemCount(): Int = day.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val weekTitle: TextView = itemView.findViewById(R.id.weeKTitle)
        private val dayNumber: TextView = itemView.findViewById(R.id.dateNumber)
        private val timeLayout: ConstraintLayout = itemView.findViewById(R.id.backDate)

        fun bind0(position: Int) {

            when (week[position]) {
                1 -> {
                    weekTitle.text = "Sun"
                }

                2 -> {
                    weekTitle.text = "Mon"
                }

                3 -> {
                    weekTitle.text = "Tue"
                }

                4 -> {
                    weekTitle.text = "Wed"
                }

                5 -> {
                    weekTitle.text = "Thu"
                }

                6 -> {
                    weekTitle.text = "Fri"
                }

                7 -> {
                    weekTitle.text = "Sat"
                }
            }

            dayNumber.text = day[position].toString()

            var count = 0
            timeLayout.setOnClickListener {
                count += 1
                if (count % 2 == 1) {
                    currentDay = position + 1

                    it.background =
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_day_selected
                        )
                    weekTitle.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white
                        )
                    )

                } else {
                    currentDay = Calendar.DAY_OF_MONTH

                    it.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.ic_day)
                    weekTitle.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.black
                        )
                    )

                }

            }

        }
    }
}

