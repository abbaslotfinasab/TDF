package com.utechia.tdf.health

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.data.entity.News
import com.utechia.domain.model.TopHealth
import com.utechia.tdf.R


class HealthAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var health: MutableList<TopHealth> = mutableListOf()


    fun addData(_health:MutableList<TopHealth>) {
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

            name.text = health[position].name
            calory.text = health[position].calory.toString()
            steps.text = health[position].steps.toString()



            Glide.with(itemView.context)
                .load(health[position].image)
                .error(R.drawable.ic_user)
                .into(image)

        }
    }
}


