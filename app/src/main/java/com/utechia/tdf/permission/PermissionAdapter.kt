package com.utechia.tdf.permission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.enum.PermissionEnum
import com.utechia.domain.model.permission.PermissionModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class PermissionAdapter(private val permissionFragment: PermissionChildFragment): PagingDataAdapter<PermissionModel, PermissionAdapter.MyViewHolder>(
    DiffUtilCallBack()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_permission, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.endDate)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val from: TextView = itemView.findViewById(R.id.startDate)
        private val status: TextView = itemView.findViewById(R.id.status)
        private val details: TextView = itemView.findViewById(R.id.btnDetails)
        private val cancel: TextView = itemView.findViewById(R.id.btnCancel)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.permissionLayout)
        private var startTimeZone = ""
        private var endTimeZone = ""



        fun bind(permission:PermissionModel) {

            startTimeZone = OffsetDateTime.parse(permission.datestarts).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            from.text = startTimeZone

            endTimeZone = OffsetDateTime.parse(permission.dateends).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            date.text = endTimeZone

            title.text = permission.type
            number.text = permission.timeLength


            when (permission.status){

                PermissionEnum.Wait.permission -> {
                    status.apply {
                        visibility = View.VISIBLE
                        text = itemView.resources.getText(R.string.wait)
                        setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.waiting))
                    }
                    cancel.visibility = View.VISIBLE


                }
                PermissionEnum.Accepted.permission ->{
                    status.apply {
                        visibility = View.VISIBLE
                        text = itemView.resources.getText(R.string.accepted)
                        setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.accepted ))
                    }
                    cancel.visibility = View.VISIBLE
                }
                PermissionEnum.Expired.permission ->{
                    cancel.visibility = View.GONE
                    status.visibility = View.GONE
                }

                PermissionEnum.Rejected.permission ->{
                    cancel.visibility = View.GONE
                    status.apply {
                        visibility = View.VISIBLE
                        text = itemView.resources.getText(R.string.rejected)
                        setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.waiting))
                    }

                }

                PermissionEnum.Cancelled.permission ->{
                    cancel.visibility = View.GONE
                    status.apply {
                        visibility = View.VISIBLE
                        text = itemView.resources.getText(R.string.cancelled)
                        setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.cancel ))
                    }
                }
            }

            cancel.setOnClickListener {
                val bundle = bundleOf(PermissionEnum.ID.permission to permission.id)
                itemView.findNavController().navigate(R.id.action_permissionFragment_to_cancelRequestFragment,bundle)
                permissionFragment.onPause()
            }

            details.setOnClickListener {
                val bundle = bundleOf("title" to  permission.type , "description" to  permission.description , "startTime" to  permission.datestarts , "endTime" to  permission.dateends)
                itemView.findNavController().navigate(R.id.action_permissionFragment_to_requestDetailsFragment,bundle)
            }

            layout.setOnClickListener {
                val bundle = bundleOf("title" to  permission.type , "description" to  permission.description , "startTime" to  permission.datestarts , "endTime" to  permission.dateends)
                itemView.findNavController().navigate(R.id.action_permissionFragment_to_requestDetailsFragment,bundle)
            }
        }
    }
    class DiffUtilCallBack : DiffUtil.ItemCallback<PermissionModel>() {
        override fun areItemsTheSame(
            oldItem: PermissionModel,
            newItem: PermissionModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PermissionModel,
            newItem: PermissionModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}


