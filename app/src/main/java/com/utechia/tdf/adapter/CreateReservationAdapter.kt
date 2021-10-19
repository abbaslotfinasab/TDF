package com.utechia.tdf.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utechia.domain.model.RoomModel
import com.utechia.tdf.R
import java.text.SimpleDateFormat
import java.util.*

class CreateReservationAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val roomModel: MutableList<RoomModel> = mutableListOf()

    var selectedRoom :MutableSet<Int> = mutableSetOf()

    var selectedTime:MutableSet<Float> = mutableSetOf()

    var finalTime = ""

    var duration = ""

    var lastPosition = -1
    var count = 0

    private var sdf = SimpleDateFormat("MM")
    private val mDate:Date = Date()
    var mCurrent = sdf.format(mDate).toInt()-1
    var currentMonth = sdf.format(mDate).toInt()-1

    private var _sdf = SimpleDateFormat("dd")
    private val dDate:Date = Date()
    var dCurrent = _sdf.format(dDate).toInt()-1
    var currentDay = _sdf.format(dDate).toInt()-1

    private val timeList:MutableList<String> = mutableListOf()
    private val tf:SimpleDateFormat = SimpleDateFormat("HH:mm")
    private val calendar:Calendar = Calendar.getInstance()

    fun addData(data: MutableList<RoomModel>){
        roomModel.clear()
        selectedRoom.clear()
        selectedTime.clear()
        timeList.clear()
        currentMonth = mCurrent
        currentDay = dCurrent
        roomModel.addAll(data)
        finalTime = ""
        duration = ""
        count = 0
        lastPosition =-1

        for(i in 0 until 24){

            calendar.set(Calendar.HOUR_OF_DAY,i)
            calendar.set(Calendar.MINUTE,0)
            timeList.add(tf.format(calendar.time))
            calendar.set(Calendar.HOUR_OF_DAY,i)
            calendar.set(Calendar.MINUTE,30)
            timeList.add(tf.format(calendar.time))

        }
        notifyItemRangeChanged(0,roomModel.size)
    }

    fun sortTime(){

        selectedTime.sorted()

        if (selectedTime.last()-selectedTime.first()<0){
            finalTime = timeList[selectedTime.last().toInt()] +"-"+timeList[selectedTime.first().toInt()]
            duration = "${(selectedTime.first() - selectedTime.last())/2} hrs"

        }
        else{
            finalTime = timeList[selectedTime.first().toInt()] +"-"+timeList[selectedTime.last().toInt()]
            duration = "${(selectedTime.last() - selectedTime.first())/2} hrs"
        }
        selectedTime.clear()
        count=0
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

                (holder as ViewHolder0).bind0()
            }

            else -> {

                (holder as ViewHolder1).bind1(position)
            }

        }
    }


    inner class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val spinner:Spinner = itemView.findViewById(R.id.months)
        private val mothRecycler:RecyclerView = itemView.findViewById(R.id.mothRecyclerView)
        private val mAdapter:DatePickerAdapter = DatePickerAdapter(this@CreateReservationAdapter)

        fun bind0(){

            mothRecycler.apply {

                layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,false)
                adapter = mAdapter
                mAdapter.addData(mCurrent)
                count=0

            }

            spinner.setSelection(mCurrent)

            spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    mAdapter.lastItemSelectedPos = -1
                    currentDay = dCurrent+1
                    count=0

                    if (position == mCurrent) {
                        mothRecycler.smoothScrollToPosition(dCurrent)
                    } else {
                        mothRecycler.smoothScrollToPosition(0)
                    }
                    Handler().postDelayed({
                        mAdapter.addData(position)
                    }, 400)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        }

    }


    inner class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val roomTitle: TextView = itemView.findViewById(R.id.roomTitle)
        private val capacity:TextView = itemView.findViewById(R.id.capacity)
        private val back:ConstraintLayout = itemView.findViewById(R.id.backArrow)
        private val forward:ConstraintLayout = itemView.findViewById(R.id.forwardArrow)
        private val recyclerView:RecyclerView = itemView.findViewById(R.id.roomRecyclerView)
        private lateinit var mAdapter:TimePickerAdapter
        private val space:Space = itemView.findViewById(R.id.last)


        fun bind1(position: Int) {

            roomTitle.text = roomModel[position-1].name

            capacity.text = roomModel[position-1].capacity.toString()

            mAdapter = TimePickerAdapter(timeList,this@CreateReservationAdapter,position-1)

            recyclerView.apply {
                adapter =mAdapter
                layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,false)

            }

            back.setOnClickListener {
                recyclerView.smoothScrollToPosition(0)
            }

            forward.setOnClickListener {
                recyclerView.smoothScrollToPosition(47)
            }

            if (position == roomModel.size){
                space.visibility = View.VISIBLE
            }

        }

    }

}