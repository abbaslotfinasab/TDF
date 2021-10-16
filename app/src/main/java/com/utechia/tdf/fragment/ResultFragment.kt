package com.utechia.tdf.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utechia.domain.moodel.ReservationModel
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentResultBinding
import com.utechia.tdf.viewmodel.ReservationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : DialogFragment() {

    private lateinit var binding: FragmentResultBinding
    private val reservationViewModel:ReservationViewModel by viewModels()
    private lateinit var reservationModel:ReservationModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        if (arguments != null) {

            reservationModel = ReservationModel(null,
                requireArguments().getString("roomTitle",""),
                requireArguments().getInt("capacity",0),
                requireArguments().getString("roomImage",""),
                requireArguments().getInt("roomId",0),
                requireArguments().getInt("currentDay", 0).toString(),
            requireArguments().getInt("currentMonth",0).toString(),
                requireArguments().getString("currentYear",""),
            requireArguments().getString("time",""),
                requireArguments().getInt("guest",0),
                requireArguments().getString("duration",""),
            requireArguments().getString("description",""),
            )

        }

        main.background = ColorDrawable(resources.getColor(R.color.white))

        dialog?.setOnDismissListener {
            reservationViewModel.reserve(reservationModel)
            findNavController().navigate(R.id.action_resultFragment_to_reservationFragment)
        }

        binding.btnConfirm.setOnClickListener {
            reservationViewModel.reserve(reservationModel)
            findNavController().navigate(R.id.action_resultFragment_to_reservationFragment)

        }

    }

}