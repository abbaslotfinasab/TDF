package com.utechia.tdf.order.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.ItemModel
import com.utechia.tdf.R

class UserOrderDetailsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: MutableList<ItemModel> = mutableListOf()

    fun addData(_orders: MutableList<ItemModel>) {
        orders.clear()
        orders.addAll(_orders)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_details_item, parent, false)
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
            number.text = "${orders[position].quantity}x"

            Glide.with(itemView.context)
                .load("${orders[position].food.imageName}")
                .centerCrop()
                .into(image)

        }


    }

}


