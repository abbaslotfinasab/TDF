package com.utechia.tdf.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.RefreshmentModel
import com.utechia.tdf.R

class PreviousOrderAdapter(private val previousOrder: PreviousOrdersFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: MutableList<RefreshmentModel> = mutableListOf()

    fun addData(_orders: MutableList<RefreshmentModel>) {
        orders.clear()
        orders.addAll(_orders)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.previous_order_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = orders.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val remove: ImageView = itemView.findViewById(R.id.exit)
        private val again: TextView = itemView.findViewById(R.id.again)


        fun bind0(position: Int) {

            name.text = orders[position].title

            Glide.with(itemView.context)
                .load(
                    if (orders[position].id==0)
                        R.mipmap.image1
                    else
                        R.mipmap.image2
                )
                .centerCrop()
                .into(image)

            remove.setOnClickListener {

            }
            again.setOnClickListener {
            }

        }
    }
}