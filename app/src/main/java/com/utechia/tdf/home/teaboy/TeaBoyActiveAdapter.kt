package com.utechia.tdf.home.teaboy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.home.TeaBoyActiveModel
import com.utechia.tdf.R


class TeaBoyActiveAdapter(private val teaBoyHomeFragment:TeaBoyHomeFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var active: MutableList<TeaBoyActiveModel> = mutableListOf()


    fun addData(_active:MutableList<TeaBoyActiveModel>) {
        active.clear()
        notifyDataSetChanged()
        active.addAll(_active)
        notifyItemRangeChanged(0,active.size)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_active_teaboy, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = active.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.title)
        private val statusTitle: TextView = itemView.findViewById(R.id.statusTitle)
        private val status: SwitchCompat = itemView.findViewById(R.id.status)


        fun bind0(position: Int) {

            name.text = "${active[position].name}th Floor"

            status.isChecked = active[position].status?:false

            if(active[position].status==false) {
                statusTitle.text = itemView.context.getText(R.string.deactive)
            }
            else{
                statusTitle.text = itemView.context.getText(R.string.active)
            }


            status.setOnCheckedChangeListener { _, isChecked ->

                active[position].id?.let { teaBoyHomeFragment.navigateToPopup(isChecked, it) }

            }

        }
    }
}


