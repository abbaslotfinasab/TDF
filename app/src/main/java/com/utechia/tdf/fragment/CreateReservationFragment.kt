package com.utechia.tdf.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.utechia.domain.model.RoomModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.adapter.TimePickerAdapter
import com.utechia.tdf.databinding.FragmentCreateReservationBinding
import com.utechia.tdf.utile.ItemDecorationReservation
import com.utechia.tdf.viewmodel.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateReservationFragment : Fragment() {

    private lateinit var binding: FragmentCreateReservationBinding
    private val roomViewModel: RoomViewModel by viewModels()
    private val roomModel:MutableList<RoomModel> = mutableListOf()
    private val timePickerAdapter:TimePickerAdapter = TimePickerAdapter()
    private val calendar: Calendar = Calendar.getInstance()
    private var day = calendar.get(Calendar.DAY_OF_MONTH)
    private var month = calendar.get(Calendar.MONTH)
    private var year = calendar.get(Calendar.YEAR)
    private var room = 0
    private var th = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val monthList = resources.getStringArray(R.array.month_array)

        binding.day.text = day.toString()
        binding.month.text = monthList[month]
        binding.year.text = year.toString()

        binding.increaseDay.setOnClickListener{
            if (day<31)
            day +=1
            binding.day.text = day.toString()

        }

        binding.decreaseDay.setOnClickListener {
            if (day>1)
            day -=1
            binding.day.text = day.toString()
        }

        binding.increaseMonth.setOnClickListener{
            if (month<11)
            month +=1
            binding.month.text = monthList[month]

        }


        binding.decreaseMonth.setOnClickListener {
            if (month>1)
            month -=1
            binding.month.text = monthList[month]
        }

        binding.increaseYear.setOnClickListener{
            if (year<2029)
            year +=1
            binding.year.text = year.toString()

        }

        binding.decreaseYear.setOnClickListener {
            if (year>2020)
                year -=1
            binding.year.text = year.toString()
        }

        binding.increaseRoom.setOnClickListener {
            if (room<roomModel.size - 1)
            room +=1
            binding.roomTitle.text = roomModel[room].name
            binding.capacity.text = roomModel[room].capacity.toString()
            timePickerAdapter.addData(roomModel[room].hour)
        }

        binding.decreaseRoom.setOnClickListener {
            if (room>0)
            room -=1
            binding.roomTitle.text = roomModel[room].name
            binding.capacity.text = roomModel[room].capacity.toString()
            timePickerAdapter.addData(roomModel[room].hour)
        }

        binding.increaseFloor.setOnClickListener {
            if (th<roomModel.size - 1)
                th +=1
            binding.floor.text = roomModel[th].floor

        }

        binding.decreaseFloor.setOnClickListener {
            if (th>0)
                th -=1
            binding.floor.text = roomModel[th].floor
        }

        binding.timeRecycler.apply {
            layoutManager =
                GridLayoutManager(requireActivity(),6)
            adapter = timePickerAdapter
            addItemDecoration(ItemDecorationReservation())

        }
        roomObserver()
    }


    private fun roomObserver() {
        roomViewModel.roomModel.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {
                    roomModel.addAll(it.data)
                    binding.prg.visibility = View.GONE
                    timePickerAdapter.addData(roomModel[room].hour)

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}

