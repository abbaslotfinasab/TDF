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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DatePickerAdapter(val createReservationAdapter: CreateReservationAdapter):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var calendar = Calendar.getInstance()
    private var day:ArrayList<Int> = arrayListOf()
    private var week:ArrayList<Int> = arrayListOf()
    private var _currentMonth = 0

    var lastItemSelectedPos = -1
    var start = 0

    fun addData(position : Int) {
        start=1
        day.clear()
        week.clear()
        _currentMonth = position
        calendar.set(Calendar.MONTH, _currentMonth)
        val s = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        val e = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in s..e) {
            calendar.set(Calendar.MONTH, _currentMonth)
            day.add(i)
            calendar.set(Calendar.DAY_OF_MONTH, i)
            week.add(calendar.get(Calendar.DAY_OF_WEEK))

        }
        notifyItemRangeChanged(0,e-1)
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

            if (position == createReservationAdapter.dCurrent &&_currentMonth==createReservationAdapter.mCurrent && start == 1|| position == lastItemSelectedPos){

             select(position)

            }
            else
                unselect()

            timeLayout.setOnClickListener {

                start=0

                notifyItemChanged(createReservationAdapter.dCurrent)

                notifyItemChanged(lastItemSelectedPos)

                lastItemSelectedPos = position

                select(position)

            }

        }

         private fun select(position: Int){

             createReservationAdapter.currentDay = position + 1
             createReservationAdapter.currentMonth = _currentMonth

             timeLayout.background =
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
        }

         private fun unselect(){

            timeLayout.background =
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

