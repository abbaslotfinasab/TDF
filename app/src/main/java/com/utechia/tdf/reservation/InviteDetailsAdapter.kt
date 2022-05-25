package com.utechia.tdf.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.enum.ReservationEnum
import com.utechia.domain.model.reservation.InviteDetailsModel
import com.utechia.tdf.R


class InviteDetailsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val userList:MutableSet<InviteDetailsModel> = mutableSetOf()

    fun addData(profileModel:InviteDetailsModel){
        userList.add(profileModel)
        notifyItemInserted(userList.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_invite_details, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = userList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val cover: ImageView = itemView.findViewById(R.id.peoplePhoto)
        private val jobTitle: TextView = itemView.findViewById(R.id.jobTitle)
        private val role: TextView = itemView.findViewById(R.id.btnRole)


        fun bind0(position: Int) {
            title.text = userList.elementAt(position).title
            if (!userList.elementAt(position).subTitle.isNullOrEmpty()) {
                jobTitle.text = userList.elementAt(position).subTitle
            }else{
                jobTitle.text = userList.elementAt(position).mail
            }

            if(userList.elementAt(position).role != ReservationEnum.Attend.reservation){
                role.text = userList.elementAt(position).role
            }else{
                role.visibility = View.GONE
            }

            Glide.with(itemView.context)
                .load("${userList.elementAt(position).cover}")
                .centerCrop()
                .placeholder(R.drawable.ic_profile_icon)
                .error(R.drawable.ic_profile_icon)
                .into(cover)

        }
    }
}