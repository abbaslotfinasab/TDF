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
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.enum.EventsEnum
import com.utechia.domain.model.event.EventModel
import com.utechia.tdf.R


class EventAdapter: PagingDataAdapter<EventModel, EventAdapter.MyViewHolder>(
    DiffUtilCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_event, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val subTitle: TextView = itemView.findViewById(R.id.subTitle)
        private val btnPublic: TextView = itemView.findViewById(R.id.status)
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val rate: RatingBar = itemView.findViewById(R.id.rating)
        private val result: AppCompatButton = itemView.findViewById(R.id.btnResult)
        private val status: TextView = itemView.findViewById(R.id.username)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.eventLayout)


        fun bind(event:EventModel) {

            title.text = event.title
            subTitle.text = event.signstatus
            status.text = event.type
            rate.rating = 5.0f

            Glide.with(itemView.context)
                .load(event.coverphoto)
                .error(R.mipmap.ic_evente_banner_foreground)
                .into(image)

            
            
            if (event.isPublic==true){
                subTitle.visibility = View.GONE
                btnPublic.visibility = View.VISIBLE
                rate.visibility = View.GONE
                if (event.status == EventsEnum.End.event){
                    result.visibility = View.VISIBLE
                    result.text = itemView.resources.getText(R.string.evaluate)
                    if (event.userrate == null){
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
                        rate.rating = event.userrate?.toFloat()?:5.0f
                    }

                }
                else{
                    result.visibility = View.GONE
                    rate.visibility = View.GONE

                }
            }
            else {
                btnPublic.visibility = View.GONE
                when (event.contribute) {

                    null -> {
                        subTitle.visibility = View.GONE
                        when (event.status) {

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

                        when (event.status) {

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

                    EventsEnum.Cancelled.event -> {

                        subTitle.visibility = View.VISIBLE
                        subTitle.text = itemView.resources.getText(R.string.cancelled)
                        subTitle.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.waiting
                            )
                        )

                        when (event.status) {

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

                        when (event.status) {

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

                        when (event.status) {

                            EventsEnum.End.event -> {
                                result.visibility = View.VISIBLE
                                result.text = itemView.resources.getText(R.string.evaluate)
                                if (event.userrate != null) {
                                    rate.visibility = View.VISIBLE
                                    result.setBackgroundColor(
                                        ContextCompat.getColor(
                                            itemView.context,
                                            R.color.disActive
                                        )
                                    )
                                    result.isEnabled = false
                                    rate.rating = event.userrate?.toFloat()!!
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
                val bundle = bundleOf(   EventsEnum.ID.event to event.id)
                itemView.findNavController().navigate(R.id.action_eventSystemFragment_to_eventDetailsFragment,bundle)
            }
            layout.setOnClickListener {
                val bundle = bundleOf(EventsEnum.ID.event  to event.id)
                itemView.findNavController().navigate(R.id.action_eventSystemFragment_to_eventDetailsFragment,bundle)
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<EventModel>() {
        override fun areItemsTheSame(
            oldItem: EventModel,
            newItem: EventModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: EventModel,
            newItem: EventModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}


