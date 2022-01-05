package com.utechia.tdf.ticket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.tdf.R

class FirebaseUploadAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var file: MutableList<String> = mutableListOf()

    fun addData(uri:MutableList<String>) {
        file.clear()
        file.addAll(uri)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_upload, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = file.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val deleteIcon: ImageView = itemView.findViewById(R.id.exit)

        fun bind0(position: Int) {

                deleteIcon.visibility = View.GONE

                Glide.with(itemView.context)
                    .load("https://sandbox.tdf.gov.sa/${file[position]}")
                    .error(R.drawable.ic_empty_upload)
                    .centerCrop()
                    .into(image)

                image.setOnClickListener {
                    /*val bundle = bundleOf("uri" to "https://sandbox.tdf.gov.sa/${file[position]}")
                    itemView.findNavController().navigate(R.id.action_createTicketFragment_to_blankFragment,bundle)
                    file.clear()*/
                }
            }
    }
}


