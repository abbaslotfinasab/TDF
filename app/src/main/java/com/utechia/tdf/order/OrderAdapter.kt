package com.utechia.tdf.order

import android.os.Handler
import android.os.Looper
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

class OrderAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: MutableList<OrderDataModel> = mutableListOf()
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
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
                .inflate(R.layout.order_item, parent, false)
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
        private val status: TextView = itemView.findViewById(R.id.status)
        private val details: TextView = itemView.findViewById(R.id.btnDetails)
        private val cancel: TextView = itemView.findViewById(R.id.btnCancel)
        private var ratingBar: RatingBar = itemView.findViewById(R.id.rating)
        private val rateNumber: TextView = itemView.findViewById(R.id.ratingNum)
        private var indicate = false

        fun bind0(position: Int) {
            indicate = false

            dateFormat = sdf.parse(orders[position].updatedAt)
            simple = SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.getDefault()).format(dateFormat)
            date.text = "$simple"

            number.text = "${orders[position].cart?.items?.size}x"
            oderId.text = "Order ID:675867468048609"

            if (orders[position].cart?.items?.size!! >1){
                title.text = orders[position].cart?.items!![0].food.title+"..."
            }
            else if (orders[position].cart?.items?.size!! !=0)
                title.text = orders[position].cart?.items!![0].food.title


            when (orders[position].status){

                "waiting" -> {

                    status.apply {
                        visibility = View.VISIBLE
                        text = "Waiting"
                        setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.waiting))
                    }
                    cancel.visibility = View.VISIBLE
                    ratingBar.visibility = View.GONE
                    rateNumber.visibility = View.GONE


                }
                "preparing" ->{
                    status.apply {
                        visibility = View.VISIBLE
                        text = "Preparing"
                        setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.prepare))
                    }
                    cancel.visibility = View.GONE
                    ratingBar.visibility = View.GONE
                    rateNumber.visibility = View.GONE


                }
                "cancelled_by_user" ->{
                    cancel.visibility = View.GONE
                    ratingBar.visibility = View.GONE
                    status.visibility = View.GONE
                    rateNumber.visibility = View.GONE

                }

                "cancelled_by_teaboy" ->{
                    cancel.visibility = View.GONE
                    ratingBar.visibility = View.GONE
                    status.visibility = View.GONE
                    rateNumber.visibility = View.GONE

                }

                "delivered" ->{
                    status.visibility = View.INVISIBLE
                    cancel.visibility = View.INVISIBLE
                    ratingBar.visibility = View.VISIBLE
                    rateNumber.visibility = View.VISIBLE
                    if (orders[position].orderrate?.isNotEmpty()!!) {
                        indicate = true
                        rateNumber.text = orders[position].orderrate!![0].rate!!.toFloat().toString()
                        ratingBar.rating = orders[position].orderrate!![0].rate!!.toFloat()
                    }
                    else
                        ratingBar.setIsIndicator(false)
                }
            }
            ratingBar.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                    if (!indicate && orders[position].orderrate!!?.isEmpty()) {
                        rateNumber.text = "${rating.toInt()}.0"

                        Handler(Looper.getMainLooper()).postDelayed({

                            val bundle = bundleOf("orderId" to orders[position].id, "rate" to rating.toInt())
                            itemView.findNavController().navigate(R.id.orderFragment_to_rateConfirmationFragment, bundle)

                        }, 300)

                    }
                    else
                        ratingBar.setIsIndicator(true)
                }

            cancel.setOnClickListener {
                val bundle = bundleOf("orderId" to orders[position].id)
                itemView.findNavController().navigate(R.id.action_orderFragment_to_cancelFragment,bundle)
            }

            details.setOnClickListener {
                val bundle = bundleOf("cartId" to orders[position].cart?.id)
                itemView.findNavController().navigate(R.id.action_orderFragment_to_orderDetailsFragment,bundle)
            }
        }
    }
}


