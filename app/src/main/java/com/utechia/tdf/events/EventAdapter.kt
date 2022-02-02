package com.utechia.tdf.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.enum.EventsEnum
import com.utechia.domain.model.EventModel
import com.utechia.tdf.R


class EventAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var event: MutableList<EventModel> = mutableListOf()

    fun addData(_event: MutableList<EventModel>) {
        event.clear()
        notifyDataSetChanged()
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
        private val btnPublic: TextView = itemView.findViewById(R.id.status)
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val rate: RatingBar = itemView.findViewById(R.id.rating)
        private val result: AppCompatButton = itemView.findViewById(R.id.btnResult)
        private val status: TextView = itemView.findViewById(R.id.username)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.eventLayout)


        fun bind0(position: Int) {

            title.text = event[position].title
            subTitle.text = event[position].signstatus
            status.text = event[position].type
            rate.rating = 5.0f

            Glide.with(itemView.context)
                .load(event[position].coverphoto)
                .error(R.mipmap.ic_evente_banner_foreground)
                .into(image)

            
            
            if (event[position].isPublic==true){
                subTitle.visibility = View.GONE
                btnPublic.visibility = View.VISIBLE
                rate.visibility = View.GONE
                if (event[position].status == EventsEnum.End.event){
                    result.visibility = View.VISIBLE
                    result.text = itemView.resources.getText(R.string.evaluate)
                    if (event[position].userrate == null){
                        rate.visibility = View.GONE
                        result.setBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.confirm
                            )
                        )
                        result.isEnabled = true
                    }else{
                        result.setBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.disActive
                            )
                        )
                        result.isEnabled = false
                        rate.visibility = View.VISIBLE
                        rate.rating = event[position].userrate?.toFloat()?:5.0f
                    }

                }
                else{
                    result.visibility = View.GONE
                    rate.visibility = View.GONE

                }
            }
            else {
                btnPublic.visibility = View.GONE
                when (event[position].contribute) {

                    null -> {
                        subTitle.visibility = View.GONE
                        when (event[position].status) {

                            EventsEnum.End.event -> {
                                result.visibility = View.GONE
                                rate.visibility = View.GONE
                            }

                            EventsEnum.Inprogress.event -> {
                                result.visibility = View.GONE
                                rate.visibility = View.GONE
                            }

                            EventsEnum.Upcoming.event -> {

                                result.visibility = View.VISIBLE
                                rate.visibility = View.GONE
                                result.text = itemView.resources.getText(R.string.apply)
                                result.setBackgroundColor(
                                    ContextCompat.getColor(
                                        itemView.context,
                                        R.color.confirm
                                    )
                                )
                                result.isEnabled = true

                            }
                        }
                    }

                    EventsEnum.Rejected.event -> {

                        subTitle.visibility = View.VISIBLE
                        subTitle.text = itemView.resources.getText(R.string.reject)
                        subTitle.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.bubble
                            )
                        )

                        when (event[position].status) {

                            EventsEnum.End.event -> {
                                result.visibility = View.VISIBLE
                                rate.visibility = View.GONE
                                result.text = itemView.resources.getText(R.string.evaluate)
                                subTitle.setBackgroundColor(
                                    ContextCompat.getColor(
                                        itemView.context,
                                        R.color.disActive
                                    )
                                )
                                result.isEnabled = false
                            }

                            EventsEnum.Inprogress.event -> {

                                result.visibility = View.GONE
                                rate.visibility = View.GONE

                            }
                            EventsEnum.Upcoming.event -> {
                                result.visibility = View.VISIBLE
                                rate.visibility = View.GONE
                                result.text = itemView.resources.getText(R.string.apply)
                                result.setBackgroundColor(
                                    ContextCompat.getColor(
                                        itemView.context,
                                        R.color.confirm
                                    )
                                )
                                result.isEnabled = true

                            }
                        }
                    }

                    EventsEnum.Cancelled.event -> {

                        subTitle.visibility = View.VISIBLE
                        subTitle.text = itemView.resources.getText(R.string.cancelled)
                        subTitle.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.waiting
                            )
                        )

                        when (event[position].status) {

                            EventsEnum.End.event -> {
                                result.visibility = View.VISIBLE
                                rate.visibility = View.GONE
                                result.text = itemView.resources.getText(R.string.evaluate)
                                result.setBackgroundColor(
                                    ContextCompat.getColor(
                                        itemView.context,
                                        R.color.disActive
                                    )
                                )
                                result.isEnabled = false
                            }

                            EventsEnum.Inprogress.event -> {

                                result.visibility = View.GONE
                                rate.visibility = View.GONE

                            }
                            EventsEnum.Upcoming.event -> {
                                result.visibility = View.VISIBLE
                                rate.visibility = View.GONE
                                result.text = itemView.resources.getText(R.string.apply)
                                result.setBackgroundColor(
                                    ContextCompat.getColor(
                                        itemView.context,
                                        R.color.confirm
                                    )
                                )
                                result.isEnabled = true

                            }
                        }

                    }

                    EventsEnum.Pending.event -> {
                        subTitle.visibility = View.VISIBLE
                        subTitle.text = itemView.resources.getText(R.string.pending)
                        subTitle.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.disActive
                            )
                        )

                        when (event[position].status) {

                            EventsEnum.End.event -> {
                                result.visibility = View.VISIBLE
                                rate.visibility = View.GONE
                                result.text = itemView.resources.getText(R.string.apply)
                                result.setBackgroundColor(
                                    ContextCompat.getColor(
                                        itemView.context,
                                        R.color.disActive
                                    )
                                )
                                result.isEnabled = false
                            }

                            EventsEnum.Inprogress.event -> {
                                result.visibility = View.VISIBLE
                                rate.visibility = View.GONE
                                result.text = itemView.resources.getText(R.string.apply)
                                result.setBackgroundColor(
                                    ContextCompat.getColor(
                                        itemView.context,
                                        R.color.disActive
                                    )
                                )
                                result.isEnabled = false
                            }
                            EventsEnum.Upcoming.event -> {
                                result.visibility = View.VISIBLE
                                rate.visibility = View.GONE
                                result.text = itemView.resources.getText(R.string.apply)
                                result.setBackgroundColor(
                                    ContextCompat.getColor(
                                        itemView.context,
                                        R.color.disActive
                                    )
                                )
                                result.isEnabled = false

                            }
                        }
                    }

                    EventsEnum.Attending.event -> {

                        subTitle.visibility = View.VISIBLE
                        subTitle.text = itemView.resources.getText(R.string.attended)
                        subTitle.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.accepted
                            )
                        )

                        when (event[position].status) {

                            EventsEnum.End.event -> {
                                result.visibility = View.VISIBLE
                                result.text = itemView.resources.getText(R.string.evaluate)
                                if (event[position].userrate != null) {
                                    rate.visibility = View.VISIBLE
                                    result.setBackgroundColor(
                                        ContextCompat.getColor(
                                            itemView.context,
                                            R.color.disActive
                                        )
                                    )
                                    result.isEnabled = false
                                    rate.rating = event[position].userrate?.toFloat()!!
                                } else {
                                    rate.visibility = View.GONE
                                    result.setBackgroundColor(
                                        ContextCompat.getColor(
                                            itemView.context,
                                            R.color.confirm
                                        )
                                    )
                                    result.isEnabled = true
                                }
                            }

                            EventsEnum.Inprogress.event -> {

                                result.visibility = View.GONE
                                rate.visibility = View.GONE

                            }
                            EventsEnum.Upcoming.event -> {

                                result.visibility = View.VISIBLE
                                result.visibility = View.VISIBLE
                                rate.visibility = View.GONE
                                result.text = itemView.resources.getText(R.string.apply)
                                result.setBackgroundColor(
                                    ContextCompat.getColor(
                                        itemView.context,
                                        R.color.disActive
                                    )
                                )
                                result.isEnabled = false

                            }
                        }
                    }
                }
            }

            result.setOnClickListener {
                val bundle = bundleOf(   EventsEnum.ID.event to event[position].id)
                itemView.findNavController().navigate(R.id.action_eventSystemFragment_to_eventDetailsFragment,bundle)
            }
            layout.setOnClickListener {
                val bundle = bundleOf(EventsEnum.ID.event  to event[position].id)
                itemView.findNavController().navigate(R.id.action_eventSystemFragment_to_eventDetailsFragment,bundle)
            }
        }
    }
}


