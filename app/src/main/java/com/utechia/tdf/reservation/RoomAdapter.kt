package com.utechia.tdf.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.reservation.RoomModel
import com.utechia.tdf.R


class RoomAdapter(val roomListFragment: RoomListFragment) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val roomList:MutableList<RoomModel> = mutableListOf()

    fun addData(_roomList:MutableList<RoomModel>){
        roomList.clear()
        notifyDataSetChanged()
        roomList.addAll(_roomList)
        notifyItemRangeChanged(0,_roomList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_select_room, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = roomList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val cover: ImageView = itemView.findViewById(R.id.coverPhoto)
        private val floor: TextView = itemView.findViewById(R.id.locationRoom)
        private val capacity: TextView = itemView.findViewById(R.id.capacityRoom)
        private val select: AppCompatButton = itemView.findViewById(R.id.btnSelect)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.roomLayout)

        fun bind0(position: Int) {

            title.text = roomList[position].name
            floor.text = "${roomList[position].floor.name}nd Floor"
            capacity.text = "capacity: ${roomList[position].capacity}"

            Glide.with(itemView.context)
                .load("${roomList[position].coverPhoto}")
                .placeholder(R.mipmap.meet_place)
                .error(R.mipmap.meet_place)
                .centerCrop()
                .into(cover)

            select.setOnClickListener {
                RoomListener.roomListener.postValue(roomList[position])
                roomListFragment.dismiss()
            }
            layout.setOnClickListener {
                RoomListener.roomListener.postValue(roomList[position])
                roomListFragment.dismiss()
            }
        }
    }
}