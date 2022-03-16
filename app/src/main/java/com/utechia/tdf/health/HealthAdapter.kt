package com.utechia.tdf.health

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.TopStepsModel
import com.utechia.tdf.R


class HealthAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var health: MutableList<TopStepsModel> = mutableListOf()


    fun addData(_health:MutableList<TopStepsModel>) {
        health.clear()
        notifyDataSetChanged()
        health.addAll(_health)
        notifyItemRangeChanged(0,health.size)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_health, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = health.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val calory: TextView = itemView.findViewById(R.id.calory)
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val steps: TextView = itemView.findViewById(R.id.steps)


        fun bind0(position: Int) {

            name.text = health[position].user.displayName
            calory.text = health[position].cal.toString()
            steps.text = health[position].count.toString()



            Glide.with(itemView.context)
                .load(health[position].user.profilePictureModel.url)
                .error(R.drawable.ic_profile_icon)
                .into(image)

        }
    }
}


