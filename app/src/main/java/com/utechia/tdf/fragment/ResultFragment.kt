package com.utechia.tdf.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.model.ReservationModel
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