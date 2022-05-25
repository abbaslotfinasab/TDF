package com.utechia.tdf.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.profile.ProfileModel
import com.utechia.tdf.R


class InvitationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val userList:MutableList<ProfileModel> = mutableListOf()

    fun addData(_userList:MutableList<ProfileModel>){
        userList.clear()
        notifyDataSetChanged()
        userList.addAll(_userList)
        notifyItemRangeChanged(0,_userList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_search_people, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = userList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val cover: ImageView = itemView.findViewById(R.id.peoplePhoto)
        private val jobTitle: TextView = itemView.findViewById(R.id.jobTitle)
        private val add: AppCompatButton = itemView.findViewById(R.id.btnAdd)
        private val remove: AppCompatButton = itemView.findViewById(R.id.btnRemove)


        fun bind0(position: Int) {
            title.text = userList[position].name
            jobTitle.text = userList[position].jobTitle

            if(userList[position].added==true){
                add.visibility = View.INVISIBLE
                remove.visibility = View.VISIBLE
            }else{
                add.visibility = View.VISIBLE
                remove.visibility = View.INVISIBLE
            }

            Glide.with(itemView.context)
                .load("${userList[position].profilePictureModel?.url}")
                .centerCrop()
                .placeholder(R.drawable.ic_profile_icon)
                .error(R.drawable.ic_profile_icon)
                .into(cover)

            add.setOnClickListener {
                InvitationListener.addInvitationListener.postValue(userList[position])
                userList[position].added=true
                it.visibility = View.INVISIBLE
                remove.visibility = View.VISIBLE
            }

            remove.setOnClickListener {
                InvitationListener.removeInvitationListener.postValue(userList[position])
                userList[position].added=false
                it.visibility = View.INVISIBLE
                add.visibility = View.VISIBLE
            }
        }
    }
}