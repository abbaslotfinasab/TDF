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

class OrderAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: MutableList<RefreshmentModel> = mutableListOf()

    fun addData(_orders: MutableList<RefreshmentModel>) {
        orders.clear()
        orders.addAll(_orders)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = orders.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val plus: TextView = itemView.findViewById(R.id.plusNumber)
        private val numberText: TextView = itemView.findViewById(R.id.numberText)
        private val minus: TextView = itemView.findViewById(R.id.minusNumber)

        fun bind0(position: Int) {
            var number = 1

            name.text = orders[position].title
            numberText.text = number.toString()

            Glide.with(itemView.context)
                .load(
                    if (orders[position].id == 0)
                        R.mipmap.image1
                    else
                        R.mipmap.image2
                )
                .centerCrop()
                .into(image)

            plus.setOnClickListener {

                number += 1
                numberText.text = number.toString()


            }

            minus.setOnClickListener {
                if (number>1)
                number -= 1


                numberText.text = number.toString()
            }
        }
    }
}

