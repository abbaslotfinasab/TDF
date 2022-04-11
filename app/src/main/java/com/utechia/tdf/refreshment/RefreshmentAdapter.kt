package com.utechia.tdf.refreshment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.refreshment.RefreshmentModel
import com.utechia.tdf.R

class RefreshmentAdapter(private val createRefreshmentFragment: CreateRefreshmentFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var refreshment:MutableList<RefreshmentModel> = mutableListOf()

    fun addData(_refreshmentModel: MutableList<RefreshmentModel>){
        refreshment.clear()
        notifyDataSetChanged()
        refreshment.addAll(_refreshmentModel)
        notifyItemRangeChanged(0,_refreshmentModel.size)

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
        private val add: TextView = itemView.findViewById(R.id.plus)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val layout: CardView = itemView.findViewById(R.id.number)
        private val plus: TextView = itemView.findViewById(R.id.plusNumber)
        private val numberText: TextView = itemView.findViewById(R.id.numberText)
        private val minus: TextView = itemView.findViewById(R.id.minusNumber)
        private val nameLayout:FrameLayout = itemView.findViewById(R.id.nameLayout)


        fun bind0(position: Int) {
            like.bringToFront()
            dislike.bringToFront()
            plus.bringToFront()
            add.bringToFront()
            nameLayout.bringToFront()
            name.bringToFront()




            name.text = refreshment[position].title
            numberText.text = (refreshment[position].number?:0).toString()
            layout.visibility = View.GONE
            add.visibility = View.VISIBLE
            dislike.visibility = View.GONE
            like.visibility = View.VISIBLE



            if (refreshment[position].like == true){
                like.visibility = View.INVISIBLE
                dislike.visibility = View.VISIBLE
            }else{
                like.visibility = View.VISIBLE
                dislike.visibility = View.INVISIBLE
            }

            like.setOnClickListener {
                it.visibility = View.GONE
                dislike.visibility = View.VISIBLE
                createRefreshmentFragment.refreshmentViewModel.like(refreshment[position].id?:0)
            }

            dislike.setOnClickListener {
                it.visibility = View.GONE
                like.visibility = View.VISIBLE
                createRefreshmentFragment.refreshmentViewModel.dislike(refreshment[position].id?:0)

            }

            if (refreshment[position].number>0){

                layout.visibility = View.VISIBLE
                add.visibility = View.GONE

            }else{
                layout.visibility = View.GONE
                add.visibility = View.VISIBLE
            }

            Glide.with(itemView.context)
                .load("${refreshment[position].imageName}")
                .centerCrop()
                .into(image)

            add.setOnClickListener {
                refreshment[position].number = refreshment[position].number?.plus(1)
                numberText.text = refreshment[position].number .toString()
                refreshment[position].open=true
                it.visibility = View.GONE
                layout.visibility = View.VISIBLE
                createRefreshmentFragment.refreshmentViewModel.postCart(refreshment[position].id?:0,refreshment[position].number?:0 )
            }
            plus.setOnClickListener {
                refreshment[position].number = refreshment[position].number?.plus(1)
                numberText.text = refreshment[position].number .toString()
                createRefreshmentFragment.refreshmentViewModel.updateCart(refreshment[position].id?:0,refreshment[position].number?:0 )
            }

            minus.setOnClickListener {
                if (refreshment[position].number?:0 >1) {
                    refreshment[position].number = refreshment[position].number?.minus(1)
                    createRefreshmentFragment.refreshmentViewModel.updateCart(
                        refreshment[position].id?:0,
                        refreshment[position].number?:0
                    )
                }
                else {
                    refreshment[position].number =0
                    layout.visibility = View.GONE
                    add.visibility = View.VISIBLE
                    createRefreshmentFragment.refreshmentViewModel.deleteCart(refreshment[position].id?:0)
                }

                numberText.text = refreshment[position].number .toString()
            }

            numberText.setOnClickListener {
                layout.visibility = View.GONE
                add.visibility = View.VISIBLE
            }
        }
    }
}