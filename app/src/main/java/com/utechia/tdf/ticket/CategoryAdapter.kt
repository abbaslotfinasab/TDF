package com.utechia.tdf.ticket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.model.CategoryModel
import com.utechia.tdf.R

class CategoryAdapter(val categoryFragment: CategoryFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var category: MutableList<CategoryModel> = mutableListOf()

    fun addData(_category:MutableList<CategoryModel>) {
        category.addAll(_category)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dropdown_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = category.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text: TextView = itemView.findViewById(R.id.textItem)

        fun bind0(position: Int) {

            text.text = category[position].title
            text.setOnClickListener {
                categoryFragment.setCategory(category[position].title!!,category[position].id!!)
            }


        }

    }
}


