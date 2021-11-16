package com.utechia.tdf.refreshment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.RefreshmentModel
import com.utechia.tdf.R

class RefreshmentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var refreshment:MutableList<RefreshmentModel> = mutableListOf()

    fun addData(_refreshmentModel: MutableList<RefreshmentModel>){
        refreshment.clear()
        refreshment.addAll(_refreshmentModel)
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.refreshment_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = refreshment.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.image)
        private val like: ImageView = itemView.findViewById(R.id.like)
        private val dislike: ImageView = itemView.findViewById(R.id.dislike)
        private val add: ImageView = itemView.findViewById(R.id.plus)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val layout: LinearLayout = itemView.findViewById(R.id.number)
        private val plus: TextView = itemView.findViewById(R.id.plusNumber)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val minus: TextView = itemView.findViewById(R.id.minusNumber)


        fun bind0(position: Int) {

            name.text = refreshment[position].title
            layout.visibility = View.GONE
            add.visibility = View.VISIBLE
            dislike.visibility = View.GONE
            like.visibility = View.VISIBLE


            like.setOnClickListener {
                it.visibility = View.GONE
                dislike.visibility = View.VISIBLE
                itemView.findNavController().navigate(R.id.action_createRefreshmentFragment_to_favoriteResultFragment)
            }

            dislike.setOnClickListener {
                it.visibility = View.GONE
                like.visibility = View.VISIBLE

            }

            Glide.with(itemView.context)
                .load(
                    if (refreshment[position].id==0)
                    R.mipmap.image1
                else
                    R.mipmap.image2
                )
                .centerCrop()
                .into(image)

            image.setOnClickListener {

                val bundle = bundleOf(
                    "mId" to refreshment[position].id,
                )

                itemView.findNavController().navigate(R.id.action_createRefreshmentFragment_to_cartFragment,bundle)

            }

            add.setOnClickListener {
                it.visibility = View.GONE
                layout.visibility = View.VISIBLE
            }
            plus.setOnClickListener {

            }

            number.setOnClickListener {
                layout.visibility = View.GONE
                add.visibility = View.VISIBLE
            }

            minus.setOnClickListener {


            }
        }
    }
}