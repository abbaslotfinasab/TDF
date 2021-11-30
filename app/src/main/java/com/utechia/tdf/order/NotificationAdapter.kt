package com.utechia.tdf.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.ItemModel
import com.utechia.domain.model.OrderDataModel
import com.utechia.tdf.R

class NotificationAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: MutableList<ItemModel> = mutableListOf()

    fun addData(_orders: MutableList<ItemModel>) {
        orders.clear()
        orders.addAll(_orders)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order_notification, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = orders.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val image: ImageView = itemView.findViewById(R.id.image)

        fun bind0(position: Int) {

            title.text = orders[position].food.title
            number.text = "2x"

            Glide.with(itemView.context)
                .load("https://sandbox.tdf.gov.sa/api/cafeteria/image/${orders[position].food.imageName}")
                .centerCrop()
                .into(image)

        }


    }

}


