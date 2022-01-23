package com.utechia.tdf.ticket

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.tdf.R

class UploadAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var localFile: MutableList<String> = mutableListOf()
    var mediaUrl:MutableSet<String> = mutableSetOf()


    fun addData(uri:String) {
        localFile.add(uri)
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

    override fun getItemCount(): Int = localFile.size+1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val deleteIcon: ImageView = itemView.findViewById(R.id.exit)

        fun bind0(position: Int) {

            if (position<localFile.size) {

                deleteIcon.visibility = View.VISIBLE
                deleteIcon.bringToFront()

                Glide.with(itemView.context)
                    .load(localFile[position])
                    .error(R.drawable.ic_empty_upload)
                    .centerCrop()
                    .into(image)
                image.setOnClickListener {
                    val bundle = bundleOf("uri" to localFile[position])
                    itemView.findNavController().navigate(R.id.action_createTicketFragment_to_blankFragment,bundle)
                }

                deleteIcon.setOnClickListener {
                    val bundle = bundleOf("position" to position ,"uri" to mediaUrl.elementAt(position))
                    itemView.findNavController().navigate(R.id.action_createTicketFragment_to_uploadDeleteFragment,bundle)
                }
            }

            else{
                deleteIcon.visibility = View.GONE

                Glide.with(itemView.context)
                    .load(R.drawable.addicon)
                    .error(R.drawable.ic_empty_upload)
                    .centerCrop()
                    .into(image)

                image.setOnClickListener {
                    itemView.findNavController().navigate(R.id.action_createTicketFragment_to_uploadFragment)
                }
            }
        }
    }
}


