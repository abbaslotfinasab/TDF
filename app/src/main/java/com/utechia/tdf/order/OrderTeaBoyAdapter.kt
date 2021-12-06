package com.utechia.tdf.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.OrderDataModel
import com.utechia.tdf.R
import java.text.SimpleDateFormat
import java.util.*

class OrderTeaBoyAdapter(private val teaBoyOrdersFragment: TeaBoyOrdersFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: MutableList<OrderDataModel> = mutableListOf()
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private lateinit var dateFormat: Date
    private var simple = ""


    fun addData(_orders: MutableList<OrderDataModel>) {
        orders.clear()
        orders.addAll(_orders)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_teaboy_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = orders.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.date)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val oderId: TextView = itemView.findViewById(R.id.subtitle)
        private val details: TextView = itemView.findViewById(R.id.btnDetails)
        private val user: TextView = itemView.findViewById(R.id.username)
        private val location: TextView = itemView.findViewById(R.id.location)
        private val reject: TextView = itemView.findViewById(R.id.btnReject)
        private val accept: TextView = itemView.findViewById(R.id.btnAccept)
        private val confirm: TextView = itemView.findViewById(R.id.btnConfirm)

        fun bind0(position: Int) {

            dateFormat = sdf.parse(orders[position].createdAt)
            simple = SimpleDateFormat("yyyy-MM-dd-HH:mm").format(dateFormat)
            date.text = "$simple"

            number.text = "${orders[position].cart.items?.size}x"
            oderId.text = "ID:675867468048609"
            user.text = "John Doe"
            location.text = orders[position].floor

            if (orders[position].cart.items?.size!! >1){
                title.text = orders[position].cart.items!![0].food.title+"..."
            }
            else
                title.text = orders[position].cart.items!![0].food.title


            when (orders[position].status){

                "waiting" -> {
                    reject.visibility = View.VISIBLE
                    accept.visibility = View.VISIBLE
                    confirm.visibility = View.INVISIBLE

                }
                "preparing" ->{
                    reject.visibility = View.GONE
                    accept.visibility = View.GONE
                    confirm.visibility = View.VISIBLE

                }
                "cancelled_by_user" ->{

                    reject.visibility = View.GONE
                    accept.visibility = View.GONE
                    confirm.visibility = View.GONE

                }

                "cancelled_by_teaboy" ->{
                    reject.visibility = View.GONE
                    accept.visibility = View.GONE
                    confirm.visibility = View.GONE

                }

                "delivered" ->{
                    reject.visibility = View.GONE
                    accept.visibility = View.GONE
                    confirm.visibility = View.GONE

                }

            }

            reject.setOnClickListener {
                val bundle = bundleOf("orderId" to orders[position].id)
                itemView.findNavController().navigate(R.id.action_teaBoyOrdersFragment_to_rejectFragment,bundle)
            }

            accept.setOnClickListener {
                val bundle = bundleOf("orderId" to orders[position].id)
                itemView.findNavController().navigate(R.id.action_teaBoyOrdersFragment_to_acceptFragment,bundle)
            }

            confirm.setOnClickListener {
                teaBoyOrdersFragment.orderViewModel.deliverOrder(orders[position].id!!)
                itemView.findNavController().navigate(R.id.action_teaBoyOrdersFragment_self)
            }

            details.setOnClickListener {
                val bundle = bundleOf("orderId" to orders[position].cart.id)
                itemView.findNavController().navigate(R.id.action_orderFragment_to_orderDetailsFragment,bundle)
            }

        }

    }

}


