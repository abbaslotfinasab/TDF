package com.utechia.tdf.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.enum.NotificationEnum
import com.utechia.domain.model.notification.NotificationModel
import com.utechia.tdf.R
import java.time.*

class NotificationAdapter: PagingDataAdapter<NotificationModel, NotificationAdapter.MyViewHolder>(
    DiffUtilCallBack()
) {

    var notification: MutableList<NotificationModel> = mutableListOf()
    private lateinit var timeZone:LocalDateTime
    private val localTime = LocalDateTime.now()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notification, parent, false)
        )

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val subTitle: TextView = itemView.findViewById(R.id.subTitle)
        private val name: TextView = itemView.findViewById(R.id.nameApp)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.notificationLayout)
        private val image: ImageView = itemView.findViewById(R.id.imageView17)


        fun bind(notification: NotificationModel) {

            title.text = notification.title
            subTitle.text = notification.body

            title.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            subTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))

            if (notification.access == NotificationEnum.Admin.notification)
            image.setBackgroundResource(R.drawable.ic_admin_notif)
            else
                image.setBackgroundResource(R.drawable.ic_push_notif)

            timeZone = OffsetDateTime.parse(notification.updatedAt).atZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime()

            val duration = Duration.between(
                timeZone.atOffset(ZoneOffset.UTC).toInstant(),
                localTime.atOffset(ZoneOffset.UTC).toInstant()
            )

            when (duration.seconds) {

                in 0 until 60 -> {
                    name.text = "${duration.seconds}s ago"
                }

                in 60 until 3600 -> {
                    name.text = "${duration.toMinutes()}m ago"
                }

                in 3600 until 43200 -> {
                    name.text = "${duration.toHours()}h ago"
                }

                in 43200 until 172800 -> {
                    name.text = itemView.resources.getText(R.string.yesterday)
                }

                in 172800 until 604800 -> {
                    name.text = "${duration.toDays()}d ago"
                }

                in 604800 until 2592000 -> {
                    name.text = "${(duration.toDays() / 7).toInt()}w ago"
                }

                in 2592000 until 31536000 -> {
                    name.text = "${(duration.toDays() / 30).toInt()}m ago"
                }

                else -> {
                    name.text = itemView.resources.getText(R.string.today)
                }
            }

            if (notification.isRead == true) {
                layout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
                name.setTextColor(ContextCompat.getColor(itemView.context, R.color.notification_read))


            } else {
                layout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.notification_unread))
                name.setTextColor(ContextCompat.getColor(itemView.context, R.color.dateRead))
            }
            layout.setOnClickListener {

                val bundle = bundleOf(
                    "nId" to notification.id,
                    "title" to notification.title,
                    "body" to notification.body
                )

                layout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
                title.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                subTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                name.setTextColor(ContextCompat.getColor(itemView.context, R.color.notification_read))
                notification.isRead=true

                itemView.findNavController().navigate(
                    R.id.action_notificationFragment_to_notificationDetailsFragment,
                    bundle
                )
            }
        }
    }
    class DiffUtilCallBack : DiffUtil.ItemCallback<NotificationModel>() {
        override fun areItemsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}


