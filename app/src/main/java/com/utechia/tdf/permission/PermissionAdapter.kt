package com.utechia.tdf.permission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.enum.PermissionEnum
import com.utechia.domain.model.PermissionModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class PermissionAdapter(private val permissionFragment: PermissionChildFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var permission: MutableList<PermissionModel> = mutableListOf()
    private var startTimeZone = ""
    private var endTimeZone = ""



    fun addData(_permission: MutableList<PermissionModel>) {
        permission.clear()
        permission.addAll(_permission)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_permission, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = permission.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.endDate)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val from: TextView = itemView.findViewById(R.id.startDate)
        private val status: TextView = itemView.findViewById(R.id.status)
        private val details: TextView = itemView.findViewById(R.id.btnDetails)
        private val cancel: TextView = itemView.findViewById(R.id.btnCancel)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.permissionLayout)



        fun bind0(position: Int) {

            startTimeZone = OffsetDateTime.parse(permission[position].datestarts).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            from.text = "$startTimeZone"

            endTimeZone = OffsetDateTime.parse(permission[position].dateends).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
            date.text = "$endTimeZone"

            title.text = permission[position].type
            number.text = permission[position].timeLength


            when (permission[position].status){

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
                val bundle = bundleOf(PermissionEnum.ID.permission to permission[position].id)
                itemView.findNavController().navigate(R.id.action_permissionFragment_to_cancelRequestFragment,bundle)
                permissionFragment.onPause()
            }

            details.setOnClickListener {
                val bundle = bundleOf("title" to  permission[position].type , "description" to  permission[position].description , "startTime" to  permission[position].datestarts , "endTime" to  permission[position].dateends)
                itemView.findNavController().navigate(R.id.action_permissionFragment_to_requestDetailsFragment,bundle)
            }

            layout.setOnClickListener {
                val bundle = bundleOf("title" to  permission[position].type , "description" to  permission[position].description , "startTime" to  permission[position].datestarts , "endTime" to  permission[position].dateends)
                itemView.findNavController().navigate(R.id.action_permissionFragment_to_requestDetailsFragment,bundle)
            }
        }
    }
}


