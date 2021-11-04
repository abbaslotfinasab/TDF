package com.utechia.tdf.refreshment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.SearchModel
import com.utechia.tdf.R

class SearchAdapter(private val kind:Int):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val searchList:MutableList<SearchModel> = mutableListOf()

    fun addData(_searchList:MutableList<SearchModel>){

        searchList.clear()
        searchList.addAll(_searchList)
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder0(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolder0).bind0(position)

    }

    override fun getItemCount(): Int = searchList.size


    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title:TextView = itemView.findViewById(R.id.title)
        private val layout:ConstraintLayout = itemView.findViewById(R.id.searchItem)

        fun bind0(position: Int) {

            title.text = searchList[position].name

            layout.setOnClickListener {

                val bundle = bundleOf(
                    "mId" to searchList[position].id,
                )

                if(kind==0)
                    itemView.findNavController().navigate(R.id.action_teaBoyFragment_to_cartFragment,bundle)
                else
                itemView.findNavController().navigate(R.id.action_createRefreshmentFragment_to_cartFragment,bundle)

            }
        }
    }
}