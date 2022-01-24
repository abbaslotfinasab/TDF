package com.utechia.tdf.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.GuestModel
import com.utechia.tdf.R


class GuestAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var guest: MutableList<GuestModel> = mutableListOf()

    fun addData(_guest: MutableList<GuestModel>) {
        guest.clear()
        notifyDataSetChanged()
        guest.addAll(_guest)
        notifyItemRangeChanged(0,_guest.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_guess_event, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = guest.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val subTitle: TextView = itemView.findViewById(R.id.subTitle)
        private val profile: ImageView = itemView.findViewById(R.id.avatar)
        private val link: ImageView = itemView.findViewById(R.id.link)
        private val browser: ImageView = itemView.findViewById(R.id.browser)
        private val username: TextView = itemView.findViewById(R.id.username)


        fun bind0(position: Int) {
            title.text = guest[position].fullname
            subTitle.text = guest[position].jobtitle
            username.text = guest[position].description

            Glide.with(itemView.context)
                .load("https://sandbox.tdf.gov.sa${guest[position].avatar}")
                .error(R.mipmap.profile)
                .into(profile)

        }

    }
}


