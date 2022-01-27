package com.utechia.tdf.order.user

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.model.UserOrderDataModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class UserOrderAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var userOrders: MutableList<UserOrderDataModel> = mutableListOf()
    private var timeZone = ""

    fun addData(_User_orders: MutableList<UserOrderDataModel>) {
        userOrders.clear()
        notifyDataSetChanged()
        userOrders.addAll(_User_orders)
        notifyItemRangeChanged(0,_User_orders.size-1)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = userOrders.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.date)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.orderLayout)
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

            timeZone = OffsetDateTime.parse(userOrders[position].updatedAt).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            date.text = "$timeZone"

            number.text = "${userOrders[position].cart?.items?.size}x"
            oderId.text = "Order ID:${userOrders[position].id}"

            if (userOrders[position].cart?.items?.size?:0 >1){
                title.text = userOrders[position].cart?.items?.get(0)?.food?.title+"..."
            }
            else if (userOrders[position].cart?.items?.size !=0)
                title.text = userOrders[position].cart?.items?.get(0)?.food?.title


            when (userOrders[position].status){

                OrderEnum.Wait.order-> {

                    status.apply {
                        visibility = View.VISIBLE
                        text = resources.getText(R.string.wait)
                        setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.waiting))
                    }
                    cancel.visibility = View.VISIBLE
                    ratingBar.visibility = View.GONE
                    rateNumber.visibility = View.GONE

                }
                OrderEnum.Prepare.order ->{

                    status.apply {
                        visibility = View.VISIBLE
                        text = resources.getText(R.string.prepare)
                        setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.prepare))
                    }
                    cancel.visibility = View.GONE
                    ratingBar.visibility = View.GONE
                    rateNumber.visibility = View.GONE


                }
                OrderEnum.UserCancel.order->{

                    cancel.visibility = View.GONE
                    ratingBar.visibility = View.GONE
                    status.visibility = View.GONE
                    rateNumber.visibility = View.GONE

                }

                OrderEnum.TeaBoyCancel.order->{

                    cancel.visibility = View.GONE
                    ratingBar.visibility = View.GONE
                    status.visibility = View.GONE
                    rateNumber.visibility = View.GONE

                }

                OrderEnum.Delivered.order ->{

                    status.visibility = View.INVISIBLE
                    cancel.visibility = View.INVISIBLE
                    ratingBar.visibility = View.VISIBLE
                    rateNumber.visibility = View.VISIBLE
                    if (userOrders[position].orderrate?.isNotEmpty() == true) {
                        indicate = true
                        rateNumber.text = userOrders[position].orderrate?.get(0)?.rate?.toFloat().toString()
                        ratingBar.rating = userOrders[position].orderrate?.get(0)?.rate?.toFloat()?:0.0f
                    }
                    else
                        ratingBar.setIsIndicator(false)
                }
            }
            ratingBar.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                    if (!indicate && userOrders[position].orderrate?.isEmpty() != false) {
                        rateNumber.text = "${rating.toInt()}.0"

                        Handler(Looper.getMainLooper()).postDelayed({

                            val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].id, "rate" to rating.toInt())
                            itemView.findNavController().navigate(R.id.orderFragment_to_rateConfirmationFragment, bundle)

                        }, 300)

                    }
                    else
                        ratingBar.setIsIndicator(true)
                }

            cancel.setOnClickListener {
                val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].id)
                itemView.findNavController().navigate(R.id.action_orderFragment_to_cancelFragment,bundle)
            }

            details.setOnClickListener {
                val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].cart?.id)
                itemView.findNavController().navigate(R.id.action_orderFragment_to_orderDetailsFragment,bundle)
            }

            layout.setOnClickListener {
                val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].cart?.id)
                itemView.findNavController().navigate(R.id.action_orderFragment_to_orderDetailsFragment,bundle)
            }
        }
    }
}


