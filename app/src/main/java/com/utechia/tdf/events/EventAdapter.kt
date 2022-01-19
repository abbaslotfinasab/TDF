package com.utechia.tdf.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.EventModel
import com.utechia.tdf.R


class EventAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var event: MutableList<EventModel> = mutableListOf()

    fun addData(_event: MutableList<EventModel>) {
        event.clear()
        event.addAll(_event)
        notifyItemRangeChanged(0,_event.size-1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_event, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = event.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val subTitle: TextView = itemView.findViewById(R.id.subTitle)
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val rate: RatingBar = itemView.findViewById(R.id.rating)
        private val result: AppCompatButton = itemView.findViewById(R.id.btnResult)
        private val status: TextView = itemView.findViewById(R.id.username)

        fun bind0(position: Int) {

            title.text = event[position].title

            result.setOnClickListener {
                val bundle = bundleOf("surveyId" to event[position].id)
                itemView.findNavController().navigate(R.id.action_surveySystemFragment_to_createSurveyFragment,bundle)
            }
        }
    }
}


