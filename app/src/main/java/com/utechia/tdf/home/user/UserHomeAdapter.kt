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
import com.utechia.domain.model.NewsModel
import com.utechia.tdf.R
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class UserHomeAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var news: MutableList<NewsModel> = mutableListOf()
    private var timeZone = ""


    fun addData(_news: MutableList<NewsModel>) {
        news.clear()
        notifyDataSetChanged()
        news.addAll(_news)
        notifyItemRangeChanged(0,_news.size)
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

            title.text = news[position].description

            timeZone = OffsetDateTime.parse(news[position].date?:"2022-01-01T10:12:31.484Z").atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            date.text = timeZone


            Glide.with(itemView.context)
                .load(news[position].coverphoto)
                .into(image)

            layout.setOnClickListener {
                val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(news[position].url?:"https:\\www.google.com"))
                ContextCompat.startActivity(itemView.context, browserIntent, null)
            }
        }
    }
}


