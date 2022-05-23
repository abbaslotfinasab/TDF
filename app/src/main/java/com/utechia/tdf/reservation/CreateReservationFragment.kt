package com.utechia.tdf.reservation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.utechia.domain.enum.ReservationEnum
import com.utechia.domain.model.reservation.DateModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateReservationBinding
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



@AndroidEntryPoint
class CreateReservationFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentCreateReservationBinding
    private val reservationDateAdapter:ReservationDateAdapter = ReservationDateAdapter(this)
    private val roomViewModel: RoomViewModel by viewModels()
    private var date = ""
    private val dateModel:MutableList<DateModel> = mutableListOf()
    private var name = ""
    private var number = ""
    private var title = ""
    private var cover = ""
    private var roomId = 0
    private var reservationDate = ""
    private var reservationDayOfWeek = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateReservationBinding.inflate(inflater, container, false)
        with(binding){
            listOf(
                btnAdd,btnRoom,btnMore,btnSelect,btnCalendarSelected,btnCalendarUnSelected
            ).forEach{button ->
                button.setOnClickListener{view -> onClick(view)}
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            title = requireArguments().getString(ReservationEnum.Title.reservation, "")
            cover = requireArguments().getString(ReservationEnum.Cover.reservation, "")
            roomId = requireArguments().getInt(ReservationEnum.ID.reservation, 0)
            reservationDate = requireArguments().getString(ReservationEnum.Date.reservation, "")
            reservationDayOfWeek = requireArguments().getString(ReservationEnum.DayOfWeek.reservation, "")
        }

        calculateDays()

        binding.dateRecycler.apply {
            adapter = reservationDateAdapter
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(DateItemDecoration())
        }
        reservationDateAdapter.addData(dateModel)

        selectRoom()
        setDate()
        roomObserver()
    }

    private fun roomObserver() {
        roomViewModel.roomModel.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {

                }

                is Result.Loading -> {

                }

                is Result.Error -> {

                }
            }
        }
    }

    private fun setDate(){

        DateListener.dateAdapterListener.observe(viewLifecycleOwner){

            binding.dateTitle.text = "${it.name?.lowercase()?.replaceFirstChar { it1 ->
                it1.uppercase()
            }}  ${it.date}"

            reservationDate = it.date.toString()
            binding.btnCalendarSelected.visibility = View.INVISIBLE
            binding.btnCalendarUnSelected.visibility = View.VISIBLE

        }
        DateListener.datePickerListener.observe(viewLifecycleOwner){

            binding.dateTitle.text = "${it.name?.lowercase()?.replaceFirstChar { it1 ->
                it1.uppercase()
            }}  ${it.date}"

            reservationDate = it.date.toString()
            reservationDateAdapter.previousIndex = -1
            reservationDateAdapter.notifyDataSetChanged()
            binding.btnCalendarSelected.visibility = View.VISIBLE
            binding.btnCalendarUnSelected.visibility = View.INVISIBLE        }
    }

    private fun selectRoom(){

        RoomListener.roomListener.observe(viewLifecycleOwner){

            binding.title.setTextColor(Color.WHITE)
            binding.btnSelect.visibility = View.GONE
            binding.imageRoom.visibility = View.VISIBLE
            binding.roomTitle.visibility = View.VISIBLE
            binding.btnRoom.visibility = View.VISIBLE
            binding.roomTitle.text = it.name

            Glide.with(requireActivity())
                .load(it.coverPhoto)
                .transform(BlurTransformation(10,2))
                .into(binding.imageRoom)

        }

    }

    private fun calculateDays(){
        dateModel.clear()
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        number = LocalDateTime.now().dayOfMonth.toString()
        name = LocalDateTime.now().dayOfWeek.name
        dateModel.add(DateModel(0,number,name,date))

        for (i in 1 until  6) {
            date = LocalDateTime.now().plusDays(i.toLong()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            number = LocalDateTime.now().plusDays(i.toLong()).dayOfMonth.toString()
            name = LocalDateTime.now().plusDays(i.toLong()).dayOfWeek.name
            dateModel.add(DateModel(i,number,name, date))
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnSelect -> {
                findNavController().navigate(R.id.action_createReservationFragment_to_roomListFragment)
            }
            binding.btnRoom -> {
                findNavController().navigate(R.id.action_createReservationFragment_to_roomListFragment)
            }
            binding.btnCalendarSelected -> {
                findNavController().navigate(R.id.action_createReservationFragment_to_datePickerFragment)
            }
            binding.btnCalendarUnSelected -> {
                findNavController().navigate(R.id.action_createReservationFragment_to_datePickerFragment)

            }
            binding.btnAdd -> {

            }
            binding.btnMore -> {

            }
        }
    }
}

