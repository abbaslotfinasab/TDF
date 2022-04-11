package com.utechia.tdf.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.reservation.ReservationModel
import com.utechia.tdf.R

class BookedAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val bookList:MutableList<ReservationModel> = mutableListOf()

    fun addData(_bookList:MutableList<ReservationModel>){

        bookList.clear()
        bookList.addAll(_bookList)

        notifyItemRangeChanged(0,_bookList.size)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder0(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_booked, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolder0).bind0(position)

    }

    override fun getItemCount(): Int = bookList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val roomTitle: TextView = itemView.findViewById(R.id.titleRoom)
        private val roomTime: TextView = itemView.findViewById(R.id.timeRoom)
        private val roomDuration: TextView = itemView.findViewById(R.id.durationRoom)
        private val roomDate: TextView = itemView.findViewById(R.id.date)
        private val more: AppCompatButton = itemView.findViewById(R.id.more)

        fun bind0(position: Int) {

            roomTitle.text = bookList[position].title
            roomDuration.text = bookList[position].duration
            roomDate.text =  bookList[position].month +"/"+bookList[position].day+"/"+bookList[position].year


                itemView.findNavController().navigate(R.id.action_reservationFragment_to_reservationDetails)


        }
    }
}