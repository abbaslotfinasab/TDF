package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.RefreshmentModel
import com.utechia.tdf.R
import com.utechia.tdf.fragment.CreateRefreshmentFragment

class RefreshmentAdapter(private val teaBoyFragment: CreateRefreshmentFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var refreshment:MutableList<RefreshmentModel> = mutableListOf()

    fun addData(_refreshmentModel: MutableList<RefreshmentModel>){
        refreshment.clear()
        refreshment.addAll(_refreshmentModel)
        notifyItemRangeChanged(0,refreshment.size)

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

        fun bind0(position: Int) {

            name.text = refreshment[position].name


            dislike.visibility = View.GONE
            like.visibility = View.VISIBLE

            if (refreshment[position].favorit==true){

                dislike.visibility = View.VISIBLE
                like.visibility = View.GONE

            }

            like.setOnClickListener {
                it.visibility = View.GONE
                dislike.visibility = View.VISIBLE
                refreshment[position].favorit = true
                teaBoyFragment.teaBoyViewModel.like(refreshment[position])
            }

            dislike.setOnClickListener {
                it.visibility = View.GONE
                like.visibility = View.VISIBLE
                teaBoyFragment.teaBoyViewModel.delete(refreshment[position])

            }

            Glide.with(itemView.context)
                .load(
                    if (position==0)
                    R.mipmap.image1
                else
                    R.mipmap.image2
                )
                .centerCrop()
                .into(image)

            add.setOnClickListener {

            }
        }
    }
}