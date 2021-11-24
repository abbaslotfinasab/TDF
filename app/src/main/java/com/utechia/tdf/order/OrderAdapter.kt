package com.utechia.tdf.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.OrderDataModel
import com.utechia.tdf.R

class OrderAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: MutableList<OrderDataModel> = mutableListOf()

    fun addData(_orders: MutableList<OrderDataModel>) {
        orders.clear()
        orders.addAll(_orders)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pending, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = orders.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.date)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val oderId: TextView = itemView.findViewById(R.id.subtitle)
        private val status: TextView = itemView.findViewById(R.id.status)
        private val details: TextView = itemView.findViewById(R.id.btnDetails)
        private val cancel: TextView = itemView.findViewById(R.id.btnCancel)

        fun bind0(position: Int) {

            date.text = orders[position].createdAt
            number.text = "${orders[position].cart.items?.size}x"
            oderId.text = "Order ID:675867468048609"

            if (orders[position].status == "waiting") {
                status.apply {
                    text = "Waiting"
                    setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.waiting))
                }
                cancel.visibility = View.VISIBLE
            } else {
                status.apply {
                    text = "Preparing"
                    setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.prepare
                        )
                    )
                }
                cancel.visibility = View.GONE
            }
            cancel.setOnClickListener {
                val bundle = bundleOf("orderId" to orders[position].id)
                itemView.findNavController().navigate(R.id.action_orderFragment_to_cancelFragment,bundle)
            }

            details.setOnClickListener {
                val bundle = bundleOf("cartId" to orders[position].cart.id)
                itemView.findNavController().navigate(R.id.action_orderFragment_to_orderDetailsFragment,bundle)
            }

        }

    }

}


