package com.utechia.tdf.reservation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.profile.ProfileModel
import com.utechia.tdf.R


class InviteGuestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val userList:MutableList<ProfileModel> = mutableListOf()

    fun addData(profileModel:ProfileModel){
        userList.add(profileModel)
        notifyItemInserted(userList.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_invite_eople, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = userList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val cover: ImageView = itemView.findViewById(R.id.peoplePhoto)
        private val jobTitle: TextView = itemView.findViewById(R.id.jobTitle)
        private val remove: ImageButton = itemView.findViewById(R.id.btnRemove)


        fun bind0(position: Int) {
            title.text = userList[position].name
            jobTitle.text = userList[position].jobTitle

            Glide.with(itemView.context)
                .load("${userList[position].profilePictureModel}")
                .centerCrop()
                .placeholder(R.drawable.ic_profile_icon)
                .error(R.drawable.ic_profile_icon)
                .into(cover)

            remove.setOnClickListener {
                userList.removeAt(position)
                notifyItemRemoved(position)
                notifyDataSetChanged()
                InvitationListener.removeGuestListener.postValue(userList[position])
            }
        }
    }
}