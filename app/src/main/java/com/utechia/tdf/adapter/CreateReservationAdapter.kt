package com.utechia.tdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.moodel.DayModel
import com.utechia.domain.moodel.RoomModel
import com.utechia.tdf.R
import java.util.*

class CreateReservationAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dayModel:MutableList<DayModel> = mutableListOf()
    private val roomModel: MutableList<RoomModel> = arrayListOf()
    private val calendar = Calendar.getInstance()



    /*fun addDay(_dayModel:MutableList<DayModel>){

        dayModel.clear()
        dayModel.addAll(_dayModel)

    }*/

    fun addData(data: MutableList<RoomModel>){
        roomModel.clear()
        roomModel.addAll(data)
        notifyItemRangeChanged(1,roomModel.size-1)
    }

    override fun getItemCount(): Int{

        return roomModel.size+1
    }


    override fun getItemViewType(position: Int): Int{

        return position
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return  when(viewType){

            0->{

                ViewHolder0( LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_date_picker, parent, false))


            }
            else->{

                ViewHolder1( LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_reservation_room, parent, false))

            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (position) {

            0 -> {

                (holder as ViewHolder0)
            }

            else -> {

                (holder as ViewHolder1).bind1(position)
            }

        }
    }


    inner class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView){

      /*  private val spinner:Spinner = itemView.findViewById(R.id.months)
        private val mothRecycler:RecyclerView = itemView.findViewById(R.id.mothRecyclerView)

        fun bind0(position: Int){
            spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val bundle = bundleOf("" to position)

                    itemView.findNavController().navigate(R.id.action_createReservationFragment_self,bundle)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    spinner.setSelection(calendar.get(Calendar.DAY_OF_MONTH))
                }

            }

            mothRecycler.apply {

                adapter = DatePickerAdapter(dayModel)
                layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,false)

            }

        }



*/
    }


    inner class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val roomTitle: TextView = itemView.findViewById(R.id.roomTitle)
        private val capacity:TextView = itemView.findViewById(R.id.capacity)
        private val roomImage:ImageView = itemView.findViewById(R.id.roomImage)
        private val recyclerView:RecyclerView = itemView.findViewById(R.id.roomRecyclerView)
        private val space:Space = itemView.findViewById(R.id.last)


        fun bind1(position: Int) {

            roomTitle.text = roomModel[position-1].name

            capacity.text = roomModel[position-1].capacity.toString()

            Glide.with(itemView.context)
                .load(roomModel[position-1].image)
                .centerCrop().centerInside()
                .error(R.drawable.room)
                .into(roomImage)

            recyclerView.apply {
                adapter = TimePickerAdapter()
                layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,false)
            }

            if (position == roomModel.size){
                space.visibility = View.VISIBLE
            }

        }

    }

}