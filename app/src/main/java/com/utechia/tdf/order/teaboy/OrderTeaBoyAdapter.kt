package com.utechia.tdf.order.teaboy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.model.TeaBoyOrderDataModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class OrderTeaBoyAdapter(private val teaBoyOrdersFragment: TeaBoyOrderChildFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

       return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_teaboy_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }


    override fun getItemCount(): Int = userOrders.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.date)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val oderId: TextView = itemView.findViewById(R.id.subtitle)
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

            when (userOrders[position].status) {

                OrderEnum.Wait.order -> {
                    reject.visibility = View.VISIBLE
                    accept.visibility = View.VISIBLE
                    confirm.visibility = View.INVISIBLE

                }
                OrderEnum.Prepare.order -> {
                    reject.visibility = View.GONE
                    accept.visibility = View.GONE
                    confirm.visibility = View.VISIBLE

                }
            }

            reject.setOnClickListener {
                val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].id)
                itemView.findNavController()
                    .navigate(R.id.action_teaBoyOrdersFragment_to_rejectFragment, bundle)
            }

            accept.setOnClickListener {
                val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].id)
                itemView.findNavController()
                    .navigate(R.id.action_teaBoyOrdersFragment_to_acceptFragment, bundle)
            }

            confirm.setOnClickListener {
                teaBoyOrdersFragment.teaBoyOrderViewModel.deliverOrder(userOrders[position].id?:0)
            }

            layout.setOnClickListener {
                val bundle = bundleOf(OrderEnum.ID.order to userOrders[position].cart?.id)
                itemView.findNavController()
                    .navigate(R.id.action_teaBoyOrdersFragment_to_teaBoyOrderDetailsFragment,bundle)
            }

        }
    }
}


