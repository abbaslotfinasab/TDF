package com.utechia.tdf.reservation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.enum.ReservationEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCancelReservationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancelReservationFragment : DialogFragment(),View.OnClickListener {

    private lateinit var binding: FragmentCancelReservationBinding
    private val reservationViewModel:ReservationViewModel by viewModels()
    private var meetId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCancelReservationBinding.inflate(inflater, container, false)
        binding.exit.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null)
            meetId = requireArguments().getInt(ReservationEnum.ID.reservation, 0)

        observer()
    }

    private fun observer() {

        reservationViewModel.reservationModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_cancelReservationFragment_to_reservationFragment)
                    dialog?.dismiss()

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.btnDelete.isEnabled = false
                    binding.btnCancel.isEnabled = false
                    binding.exit.isEnabled = false


                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_cancelReservationFragment_to_reservationFragment)
                    dialog?.dismiss()
                }
            }
        }
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.exit ->{
                dialog?.dismiss()
            }

            R.id.btnCancel ->{
                dialog?.dismiss()
            }

            R.id.btnDelete ->{
                reservationViewModel.cancelMeeting(meetId)
            }
        }
    }
}