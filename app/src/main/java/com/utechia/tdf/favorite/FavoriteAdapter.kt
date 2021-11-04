package com.utechia.tdf.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.RefreshmentModel
import com.utechia.tdf.R

class FavoriteAdapter(private val favoriteFragment: FavoriteFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var favorite: MutableList<RefreshmentModel> = mutableListOf()

    fun addData(_favorite: MutableList<RefreshmentModel>) {
        favorite.clear()
        favorite.addAll(_favorite)
        notifyDataSetChanged()
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
        private val remove:ImageView = itemView.findViewById(R.id.remove)


        fun bind0(position: Int) {

            Glide.with(itemView.context)
                .load(
                    if (favorite[position].id==0)
                        R.mipmap.image1
                    else
                        R.mipmap.image2
                )
                .centerCrop()
                .into(image)

            title.text = favorite[position].name

            remove.setOnClickListener {
                favoriteFragment.refreshmentViewModel.delete(favorite[position].id!!)
                favorite.removeAt(position)
                notifyItemChanged(position)
            }
        }
    }
}