package com.utechia.tdf.permission

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
import com.utechia.domain.model.PermissionModel
import com.utechia.tdf.R
import java.text.SimpleDateFormat
import java.util.*

class PermissionAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var permission: MutableList<PermissionModel> = mutableListOf()
    var sdf:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private lateinit var dateFormat:Date
    private var simple = ""

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


        fun bind0(position: Int) {

            dateFormat = sdf.parse(permission[position].datestarts)
            simple = SimpleDateFormat("yyyy-MM-dd-HH:mm").format(dateFormat)
            from.text = "${simple}"

            dateFormat = sdf.parse(permission[position].dateends)
            simple = SimpleDateFormat("yyyy-MM-dd-HH:mm").format(dateFormat)
            date.text = "${simple}"

            title.text = permission[position].type
            number.text = permission[position].timeLength


            when (permission[position].status){

                "waiting" -> {
                    status.apply {
                        visibility = View.VISIBLE
                        text = "Waiting"
                        setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.waiting))
                    }
                    cancel.visibility = View.VISIBLE


                }
                "accepted" ->{
                    status.apply {
                        visibility = View.VISIBLE
                        text = "Accepted"
                        setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.accepted ))
                    }
                    cancel.visibility = View.VISIBLE


                }
                "expired" ->{
                    cancel.visibility = View.GONE
                    status.visibility = View.GONE
                }

                "rejected" ->{
                    cancel.visibility = View.GONE
                    status.apply {
                        visibility = View.VISIBLE
                        text = "Rejected"
                        setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.waiting))
                    }

                }

                "cancelled" ->{
                    cancel.visibility = View.GONE
                    status.apply {
                        visibility = View.VISIBLE
                        text = "Cancelled"
                        setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.cancel ))
                    }


                }

            }

            cancel.setOnClickListener {
                val bundle = bundleOf("permissionId" to permission[position].id)
                itemView.findNavController().navigate(R.id.action_permissionFragment_to_cancelRequestFragment,bundle)
            }

            details.setOnClickListener {
                val bundle = bundleOf("permissionId" to permission[position].id)
                itemView.findNavController().navigate(R.id.action_permissionFragment_to_requestDetailsFragment,bundle)
            }

        }

    }

}


