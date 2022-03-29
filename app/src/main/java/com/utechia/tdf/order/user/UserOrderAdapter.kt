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
        private val time: TextView = itemView.findViewById(R.id.time)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.orderLayout)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val oderId: TextView = itemView.findViewById(R.id.subtitle)
        private val status: TextView = itemView.findViewById(R.id.status)
        private val cancel: TextView = itemView.findViewById(R.id.btnCancel)
        private var ratingBar: RatingBar = itemView.findViewById(R.id.rating)
        private val rateNumber: TextView = itemView.findViewById(R.id.ratingNum)
        private val location: TextView = itemView.findViewById(R.id.location)


        fun bind0(position: Int) {

            timeZone = OffsetDateTime.parse(userOrders[position].updatedAt).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            date.text = "$timeZone"


            timeZone = OffsetDateTime.parse(userOrders[position].updatedAt).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))
            time.text = "$timeZone"


            number.text = "${userOrders[position].cart?.items?.size}x"
            oderId.text = "${userOrders[position].id}"
            location.text = userOrders[position].location

            if (userOrders[position].cart?.items?.size?:0 >1){
                title.text = userOrders[position].cart?.items?.get(0)?.food?.title+"..."
            }
            else if (userOrders[position].cart?.items?.size !=0)
                title.text = userOrders[position].cart?.items?.get(0)?.food?.title


            when (userOrders[position].status) {

                OrderEnum.Wait.order -> {

                    status.apply {
                        visibility = View.VISIBLE
                        text = resources.getText(R.string.wait)
                        setBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.waiting
                            )
                        )
                    }
                    cancel.visibility = View.VISIBLE
                    ratingBar.visibility = View.GONE
                    rateNumber.visibility = View.GONE

                    cancel.setOnClickListener {
                        val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].id)
                        itemView.findNavController().navigate(R.id.action_orderFragment_to_cancelFragment,bundle)
                    }
                }
                OrderEnum.Prepare.order -> {

                    status.apply {
                        visibility = View.VISIBLE
                        text = resources.getText(R.string.prepare)
                        setBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.prepare
                            )
                        )
                    }
                    cancel.visibility = View.GONE
                    ratingBar.visibility = View.GONE
                    rateNumber.visibility = View.GONE

                    cancel.text = itemView.resources.getText(R.string.cancel)
                    cancel.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.cancel))
                    cancel.setOnClickListener {
                        val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].id)
                        itemView.findNavController().navigate(R.id.action_orderFragment_to_cancelFragment,bundle)
                    }


                }
                OrderEnum.UserCancel.order -> {

                    cancel.visibility = View.GONE
                    ratingBar.visibility = View.GONE
                    status.visibility = View.GONE
                    rateNumber.visibility = View.GONE

                }

                OrderEnum.TeaBoyCancel.order -> {

                    cancel.visibility = View.GONE
                    ratingBar.visibility = View.GONE
                    status.visibility = View.GONE
                    rateNumber.visibility = View.GONE

                }

                OrderEnum.Delivered.order -> {
                    status.visibility = View.GONE
                    if (userOrders[position].orderrate?.isNotEmpty() == true) {
                        rateNumber.visibility = View.VISIBLE
                        ratingBar.visibility = View.VISIBLE
                        cancel.visibility = View.GONE
                        ratingBar.rating =
                            userOrders[position].orderrate?.get(0)?.rate?.toFloat() ?: 0.0f
                        rateNumber.text = (userOrders[position].orderrate?.get(0)?.rate?.toFloat()
                            ?: 0.0f.toString()).toString()
                        ratingBar.setIsIndicator(true)
                    } else {
                        ratingBar.visibility = View.GONE
                        rateNumber.visibility = View.GONE
                        cancel.visibility = View.VISIBLE

                    }
                    cancel.text = itemView.resources.getText(R.string.evaluate)
                    cancel.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.confirm))
                    cancel.setOnClickListener {
                        val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].id)
                        itemView.findNavController().navigate(R.id.orderFragment_to_rateConfirmationFragment,bundle)
                    }
                }
            }

            layout.setOnClickListener {
                val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].cart?.id)
                itemView.findNavController().navigate(R.id.action_orderFragment_to_orderDetailsFragment,bundle)
            }
        }
    }
}


