package com.utechia.tdf.refreshment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.CartModel
import com.utechia.domain.model.ItemModel
import com.utechia.domain.model.RefreshmentModel
import com.utechia.tdf.R

class CartAdapter(private val cartFragment: CartFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var carts: MutableList<ItemModel> = mutableListOf()

    fun addData(_carts: MutableList<ItemModel>) {
        carts.clear()
        carts.addAll(_carts)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = carts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val subtitle: TextView = itemView.findViewById(R.id.subtitle)
        private val plus: TextView = itemView.findViewById(R.id.plusNumber)
        private val numberText: TextView = itemView.findViewById(R.id.numberText)
        private val minus: TextView = itemView.findViewById(R.id.minusNumber)

        fun bind0(position: Int) {
            var number = 1

            title.text = "tea"
            numberText.text = number.toString()

            Glide.with(itemView.context)
                .load(
                    if (carts[position].id == 0)
                        R.mipmap.image1
                    else
                        R.mipmap.image2
                )
                .centerCrop()
                .into(image)

            plus.setOnClickListener {

                number += 1
                numberText.text = number.toString()
                cartFragment.cartViewModel.updateCart(carts[position].id!!,number)

            }

            minus.setOnClickListener {
                if (number>1) {
                    number -= 1
                    cartFragment.cartViewModel.updateCart(
                        carts[position].id!!,
                        number
                    )
                }
                else
                    cartFragment.cartViewModel.deleteCart(carts[position].id!!)

                numberText.text = number.toString()
            }
        }
    }
}

