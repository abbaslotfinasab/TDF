package com.utechia.tdf.order.teaboy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.model.order.TeaBoyOrderDataModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class OrderTeaBoyAdapter(val teaBoyOrderChildFragment:TeaBoyOrderChildFragment): PagingDataAdapter<TeaBoyOrderDataModel, OrderTeaBoyAdapter.MyViewHolder>(
    DiffUtilCallBack()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_teaboy_item, parent, false)
        )


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
}

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.date)
        private val time: TextView = itemView.findViewById(R.id.time)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val oderId: TextView = itemView.findViewById(R.id.subtitle)
        private val user: TextView = itemView.findViewById(R.id.username)
        private val location: TextView = itemView.findViewById(R.id.location)
        private val reject: TextView = itemView.findViewById(R.id.btnCancel)
        private val accept: TextView = itemView.findViewById(R.id.btnAccept)
        private val details: TextView = itemView.findViewById(R.id.btnDetails)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.orderLayout)
        private var timeZone = ""

        fun bind(teaBoyOrderDataModel: TeaBoyOrderDataModel) {

            timeZone = OffsetDateTime.parse(teaBoyOrderDataModel.updatedAt?:"2022-01-01T10:12:31.484Z").atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            date.text = timeZone

            timeZone = OffsetDateTime.parse(teaBoyOrderDataModel.updatedAt?:"2022-01-01T10:12:31.484Z").atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))
            time.text = timeZone

            number.text = "${teaBoyOrderDataModel.cart?.items?.size}x"
            oderId.text = "${teaBoyOrderDataModel.id}"
            user.text = teaBoyOrderDataModel.user?.displayName
            location.text = "${teaBoyOrderDataModel.floor}-${teaBoyOrderDataModel.location}"

            if (teaBoyOrderDataModel.cart?.items?.size!! > 1) {
                title.text = teaBoyOrderDataModel.cart?.items?.get(0)?.food?.title + "..."
            } else if (teaBoyOrderDataModel.cart?.items?.size != 0)
                title.text = teaBoyOrderDataModel.cart?.items?.get(0)?.food?.title

            when (teaBoyOrderDataModel.status) {

                OrderEnum.Wait.order -> {
                    reject.visibility = View.VISIBLE
                    accept.apply {
                        visibility = View.VISIBLE
                        text = resources.getText(R.string.accept)

                        setOnClickListener {
                            val bundle = bundleOf(OrderEnum.ID.order to teaBoyOrderDataModel.id)
                            itemView.findNavController()
                                .navigate(R.id.action_teaBoyOrdersFragment_to_acceptFragment, bundle)
                        }                        }
                    }
                OrderEnum.Prepare.order -> {
                    reject.visibility = View.GONE
                    accept.apply {
                        visibility = View.VISIBLE
                        text = resources.getText(R.string.complete)

                        setOnClickListener {

                        }
                    }

                }

                OrderEnum.UserCancel.order -> {
                    reject.visibility = View.GONE
                    accept.visibility = View.GONE

                }

                OrderEnum.TeaBoyCancel.order -> {
                    reject.visibility = View.GONE
                    accept.visibility = View.GONE

                }

                OrderEnum.Delivered.order -> {
                    reject.visibility = View.GONE
                    accept.visibility = View.GONE
                }
            }

            reject.setOnClickListener {
                val bundle = bundleOf(OrderEnum.ID.order to teaBoyOrderDataModel.id)
                itemView.findNavController()
                    .navigate(R.id.action_teaBoyOrdersFragment_to_rejectFragment, bundle)
            }

            layout.setOnClickListener {
                val bundle = bundleOf(OrderEnum.ID.order to teaBoyOrderDataModel.cart?.id)
                itemView.findNavController()
                    .navigate(R.id.action_teaBoyOrdersFragment_to_teaBoyOrderDetailsFragment,bundle)
            }

            details.setOnClickListener {
                val bundle = bundleOf(OrderEnum.ID.order to teaBoyOrderDataModel.cart?.id)
                itemView.findNavController()
                    .navigate(R.id.action_teaBoyOrdersFragment_to_teaBoyOrderDetailsFragment,bundle)
            }

        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<TeaBoyOrderDataModel>() {
        override fun areItemsTheSame(
            oldItem: TeaBoyOrderDataModel,
            newItem: TeaBoyOrderDataModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TeaBoyOrderDataModel,
            newItem: TeaBoyOrderDataModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}


