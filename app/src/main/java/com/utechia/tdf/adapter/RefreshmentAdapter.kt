package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.RefreshmentModel
import com.utechia.tdf.R
import com.utechia.tdf.fragment.CreateRefreshmentFragment

class RefreshmentAdapter(private val teaBoyFragment: CreateRefreshmentFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var refreshment:MutableList<RefreshmentModel> = mutableListOf()
    var orders:MutableList<RefreshmentModel> = mutableListOf()

    fun addData(_refreshmentModel: MutableList<RefreshmentModel>){
        refreshment.clear()
        orders.clear()
        refreshment.addAll(_refreshmentModel)
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.refreshment_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind0(position)
    }

    override fun getItemCount(): Int = refreshment.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.image)
        private val like: ImageView = itemView.findViewById(R.id.like)
        private val dislike: ImageView = itemView.findViewById(R.id.dislike)
        private val add: ImageView = itemView.findViewById(R.id.plus)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val layout: LinearLayout = itemView.findViewById(R.id.number)
        private val plus: TextView = itemView.findViewById(R.id.plusNumber)
        private val number: TextView = itemView.findViewById(R.id.numberText)
        private val minus: TextView = itemView.findViewById(R.id.minusNumber)


        fun bind0(position: Int) {

            name.text = refreshment[position].name
            layout.visibility = View.GONE
            add.visibility = View.VISIBLE
            number.text = refreshment[position].number.toString()
            dislike.visibility = View.GONE
            like.visibility = View.VISIBLE

            if (refreshment[position].favorit==true){

                dislike.visibility = View.VISIBLE
                like.visibility = View.GONE

            }

            like.setOnClickListener {
                it.visibility = View.GONE
                dislike.visibility = View.VISIBLE
                refreshment[position].favorit=true
                teaBoyFragment.teaBoyViewModel.like(refreshment[position])
                itemView.findNavController().navigate(R.id.action_createRefreshmentFragment_to_favoriteResultFragment)
            }

            dislike.setOnClickListener {
                it.visibility = View.GONE
                like.visibility = View.VISIBLE
                refreshment[position].favorit=false
                teaBoyFragment.teaBoyViewModel.delete(position)

            }

            Glide.with(itemView.context)
                .load(
                    if (refreshment[position].id==0)
                    R.mipmap.image1
                else
                    R.mipmap.image2
                )
                .centerCrop()
                .into(image)

            image.setOnClickListener {

                val bundle = bundleOf(
                    "mId" to position,
                )

                itemView.findNavController().navigate(R.id.action_createRefreshmentFragment_to_cartFragment,bundle)

            }

            add.setOnClickListener {
                it.visibility = View.GONE
                layout.visibility = View.VISIBLE
            }
            plus.setOnClickListener {
                orders.clear()
                refreshment[position].number = refreshment[position].number?.plus(1)
                number.text = refreshment[position].number .toString()
                orders.add(refreshment[position])

            }

            number.setOnClickListener {
                layout.visibility = View.GONE
                add.visibility = View.VISIBLE
            }

            minus.setOnClickListener {

                if (refreshment[position].number!! >1) {
                    refreshment[position].number = refreshment[position].number?.minus(1)
                    number.text = refreshment[position].number.toString()
                    orders[refreshment[position].id!!] = refreshment[position]
                }
                else {
                    if (orders.size>0)
                    orders.removeAt(refreshment[position].id!!)
                    number.text = "0"
                }
            }
        }
    }
}