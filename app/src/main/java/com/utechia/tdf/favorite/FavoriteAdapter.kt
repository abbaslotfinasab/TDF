package com.utechia.tdf.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.FavoriteModel
import com.utechia.tdf.R

class FavoriteAdapter(private val favoriteFragment: FavoriteFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var favorite: MutableList<FavoriteModel> = mutableListOf()

    fun addData(_favorite: MutableList<FavoriteModel>) {
        favorite.clear()
        notifyDataSetChanged()
        favorite.addAll(_favorite)
        notifyItemRangeChanged(0,_favorite.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = favorite.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image:ImageView = itemView.findViewById(R.id.image)
        private val title:TextView = itemView.findViewById(R.id.title)
        private val subTitle:TextView = itemView.findViewById(R.id.subTitle)
        private val like: ImageView = itemView.findViewById(R.id.like)
        private val dislike: ImageView = itemView.findViewById(R.id.dislike)


        fun bind0(position: Int) {

            title.text = favorite[position].food?.title
            val number = (0..20).random()
            subTitle.text = "Ordered $number times in total"
            dislike.visibility = View.VISIBLE
            like.visibility = View.GONE


            like.setOnClickListener {
                it.visibility = View.GONE
                dislike.visibility = View.VISIBLE
                favoriteFragment.favoriteViewModel.like(favorite[position].food?.id!!)
            }

            dislike.setOnClickListener {
                it.visibility = View.GONE
                like.visibility = View.VISIBLE
                favoriteFragment.favoriteViewModel.dislike(favorite[position].food?.id!!)

            }

            Glide.with(itemView.context)
                .load(favorite[position].food?.imageName)
                .centerCrop()
                .into(image)

        }
    }
}