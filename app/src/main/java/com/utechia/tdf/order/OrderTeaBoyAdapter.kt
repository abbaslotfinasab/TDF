package com.utechia.tdf.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.TeaBoyOrderDataModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class OrderTeaBoyAdapter(private val teaBoyOrdersFragment: TeaBoyOrdersFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var userOrders: MutableList<TeaBoyOrderDataModel> = mutableListOf()
    private var timeZone = ""

    fun addData(_teaBoyOrders: MutableList<TeaBoyOrderDataModel>) {
        userOrders.clear()
        notifyDataSetChanged()
        userOrders.addAll(_teaBoyOrders)
        notifyItemRangeChanged(0, _teaBoyOrders.size - 1)

    }


    override fun getItemViewType(position: Int):Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (userOrders[viewType].status) {
            "waiting" -> {
                ViewHolder0(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.order_teaboy_item, parent, false)
                )
            }
            "preparing" -> {
                ViewHolder0(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.order_teaboy_item, parent, false)
                )
            }
            else -> {
                ViewHolder1(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_order_teaboy_delivered, parent, false)
                )
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (userOrders[position].status) {
            "waiting" -> {
                (holder as ViewHolder0).bind0(position)
            }
            "preparing" -> {
                (holder as ViewHolder0).bind0(position)
            }
            else -> {
                (holder as ViewHolder1).bind0(position)
            }
        }
    }

    override fun getItemCount(): Int = userOrders.size

    inner class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        private val layout: ConstraintLayout = itemView.findViewById(R.id.orderLayout)

        fun bind0(position: Int) {

            timeZone = OffsetDateTime.parse(userOrders[position].updatedAt).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            date.text = "$timeZone"

            number.text = "${userOrders[position].cart?.items?.size}x"
            oderId.text = "${userOrders[position].id}"
            user.text = userOrders[position].user?.displayName
            location.text = userOrders[position].user?.officeFloor

            if (userOrders[position].cart?.items?.size!! > 1) {
                title.text = userOrders[position].cart?.items!![0].food.title + "..."
            } else if (userOrders[position].cart?.items?.size!! != 0)
                title.text = userOrders[position].cart?.items!![0].food.title


            user.text = userOrders[position].user?.displayName
            location.text = userOrders[position].user?.officeFloor


            reject.setOnClickListener {
                val bundle = bundleOf("orderId" to userOrders[position].id)
                itemView.findNavController()
                    .navigate(R.id.action_teaBoyOrdersFragment_to_rejectFragment, bundle)
            }

            accept.setOnClickListener {
                val bundle = bundleOf("orderId" to userOrders[position].id)
                itemView.findNavController()
                    .navigate(R.id.action_teaBoyOrdersFragment_to_acceptFragment, bundle)
            }

            confirm.setOnClickListener {
                teaBoyOrdersFragment.userOrderViewModel.deliverOrder(userOrders[position].id!!)
                itemView.findNavController().navigate(R.id.action_teaBoyOrdersFragment_self)
            }

            details.setOnClickListener {
                val bundle = bundleOf("orderId" to userOrders[position].cart?.id)
                itemView.findNavController()
                    .navigate(R.id.action_orderFragment_to_orderDetailsFragment, bundle)
            }
            layout.setOnClickListener {
                val bundle = bundleOf("orderId" to userOrders[position].cart?.id)
                itemView.findNavController()
                    .navigate(R.id.action_orderFragment_to_orderDetailsFragment, bundle)
            }

        }
    }

    inner class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.date)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val oderId: TextView = itemView.findViewById(R.id.subtitle)
        private val details: TextView = itemView.findViewById(R.id.btnDetails)
        private val user: TextView = itemView.findViewById(R.id.username)
        private val location: TextView = itemView.findViewById(R.id.location)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.orderLayout)

        fun bind0(position: Int) {

            timeZone = OffsetDateTime.parse(userOrders[position].updatedAt).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            date.text = "$timeZone"

            number.text = "${userOrders[position].cart?.items?.size}x"
            oderId.text = "${userOrders[position].id}"
            user.text = userOrders[position].user?.displayName
            location.text = userOrders[position].user?.officeFloor

            if (userOrders[position].cart?.items?.size!! > 1) {
                title.text = userOrders[position].cart?.items!![0].food.title + "..."
            } else if (userOrders[position].cart?.items?.size!! != 0)
                title.text = userOrders[position].cart?.items!![0].food.title



            user.text = userOrders[position].user?.displayName
            location.text = userOrders[position].user?.officeFloor

            details.setOnClickListener {
                val bundle = bundleOf("orderId" to userOrders[position].cart?.id)
                itemView.findNavController()
                    .navigate(R.id.action_orderFragment_to_orderDetailsFragment, bundle)
            }
            layout.setOnClickListener {
                val bundle = bundleOf("orderId" to userOrders[position].cart?.id)
                itemView.findNavController()
                    .navigate(R.id.action_orderFragment_to_orderDetailsFragment, bundle)
            }
        }
    }
}


