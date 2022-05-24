package com.utechia.tdf.reservation

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.reservation.DateModel
import com.utechia.tdf.R


class ReservationDateAdapter(private val createReservationFragment: CreateReservationFragment):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dateList: MutableList<DateModel> = mutableListOf()
    var previousIndex = -1


    fun addData(data: MutableList<DateModel>) {
        dateList.clear()
        notifyDataSetChanged()
        dateList.addAll(data)
        notifyItemRangeChanged(0, dateList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_date, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolder).bind0(position)

    }

    override fun getItemCount(): Int = dateList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayNumber: TextView = itemView.findViewById(R.id.dateNumber)
        private val dayTitle: TextView = itemView.findViewById(R.id.weeKTitle)
        private val dateLayout: ConstraintLayout = itemView.findViewById(R.id.dateLayout)

        fun bind0(position: Int) {

            dayNumber.text = dateList[position].number.toString()
            dayTitle.text = dateList[position].name

            if(position!=previousIndex) {
                dateLayout.setBackgroundColor(Color.parseColor("#EDEDF3"))
                dayNumber.setTextColor(Color.BLACK)
                dayTitle.setTextColor(Color.BLACK)
            }

            dateLayout.setOnClickListener {
                previousIndex = position
                dateLayout.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.btnCalendarBack))
                dayNumber.setTextColor(Color.WHITE)
                dayTitle.setTextColor(Color.WHITE)
                DateListener.dateAdapterListener.postValue(dateList[position])
                notifyDataSetChanged()
            }
        }
    }
}