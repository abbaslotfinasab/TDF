package com.utechia.tdf.home.user

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.data.entity.News
import com.utechia.tdf.R


class UserHomeAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var news: MutableList<News> = mutableListOf()


    fun addData(_news:News) {
        news.add(_news)
        notifyItemChanged(news.size)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_news, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = news.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.newsLayout)


        fun bind0(position: Int) {

            title.text = news[position].title
            date.text = news[position].date


            Glide.with(itemView.context)
                .load(
                    if(position%2==0){
                        R.mipmap.news
                    }else{
                        R.mipmap.news2
                    }
                )
                .error(R.mipmap.news)
                .into(image)

            layout.setOnClickListener {
                val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(news[position].link?:"https:\\www.google.com"))
                ContextCompat.startActivity(itemView.context, browserIntent, null)
            }
        }
    }
}


