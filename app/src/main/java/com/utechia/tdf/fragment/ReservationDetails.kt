package com.utechia.tdf.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentReservationDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ReservationDetails : Fragment() {

    private lateinit var binding: FragmentReservationDetailsBinding

    private val calendar:Calendar = Calendar.getInstance()

    private var sdf = SimpleDateFormat("MM")
    private val mDate: Date = Date()
    private var currentMonth = sdf.format(mDate).toInt()

    private var _sdf = SimpleDateFormat("dd")
    private val dDate: Date = Date()
    private var currentDay = _sdf.format(dDate).toInt()

    private var month:ArrayList<String> = arrayListOf()

    private var roomId = 0
    private var capacity = 0
    private var roomTitle = ""
    private var imageRoom = ""
    private var time = ""
    private var duration = ""
    private var guest = 0
    private var description = ""
    private var year = calendar.get(Calendar.YEAR)
    private var kind = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        month.add("January")
        month.add("February")
        month.add("March")
        month.add("April")
        month.add("May")
        month.add("June")
        month.add("July")
        month.add("August")
        month.add("September")
        month.add("October")
        month.add("November")
        month.add("December")

        val main: ConstraintLayout = activity?.findViewById(R.id.mainLayout)!!
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        val notificationIcon: ImageView = requireActivity().findViewById(R.id.notification)
        val custom: ImageView = requireActivity().findViewById(R.id.customButton)
        val menu: ImageView = requireActivity().findViewById(R.id.menu)
        val back: ConstraintLayout = requireActivity().findViewById(R.id.back)
        val name: TextView = requireActivity().findViewById(R.id.name)
        val title: TextView = requireActivity().findViewById(R.id.title)
        val subTitle: TextView = requireActivity().findViewById(R.id.subTitle)
        navBar.visibility = View.GONE
        notificationIcon.isEnabled = false
        custom.visibility = View.GONE
        menu.visibility = View.INVISIBLE
        back.visibility = View.VISIBLE
        name.visibility = View.GONE
        title.visibility = View.VISIBLE
        subTitle.visibility = View.VISIBLE

        main.background = ColorDrawable(resources.getColor(R.color.white))

        if (arguments != null) {

            currentMonth = requireArguments().getInt("currentMonth",currentMonth)
            currentDay = requireArguments().getInt("currentDay", currentDay)
            roomId = requireArguments().getInt("roomId",0)
            roomTitle = requireArguments().getString("roomTitle","")
            capacity = requireArguments().getInt("capacity",0)
            time = requireArguments().getString("time","")
            duration = requireArguments().getString("duration","")
            imageRoom = requireArguments().getString("imageRoom","")
            description = requireArguments().getString("description","")
            kind = requireArguments().getInt("kind",0)

        }

        binding.roomTitle.text = roomTitle
        binding.capacity.text = capacity.toString()
        binding.time.text = time
        binding.duration.text = duration
        binding.dateDay.text =  currentDay.toString()
        binding.dateMonth.text =  month[currentMonth]
        binding.dateYear.text =  year.toString()
        binding.description.setText(description)

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        if (kind ==0)
        binding.btnConfirm.text = "Book Now"
        else
            binding.btnConfirm.text = "Edit Meeting"


        binding.btnConfirm.setOnClickListener {
            description = binding.description.text.toString()
            val bundle = bundleOf(
                "currentDay" to currentDay,
                "currentMonth" to currentMonth+1,
                "roomId" to roomId,
                "roomTitle" to roomTitle,
                "capacity" to capacity,
                "imageRoom" to imageRoom,
                "currentYear" to year.toString(),
                "time" to time,
                "duration" to duration,
                "guest" to guest,
                "description" to description
            )

            if (kind ==0) {
                findNavController().navigate(
                    R.id.action_reservationDetails_to_resultFragment,
                    bundle
                )
            }

            else{
                findNavController().navigate(R.id.action_reservationDetails_to_createReservationFragment)
            }
        }


    }

}