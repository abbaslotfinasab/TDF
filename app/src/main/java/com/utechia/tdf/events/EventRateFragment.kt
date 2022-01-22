package com.utechia.tdf.events

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentEventRateBinding
import com.utechia.tdf.ticket.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EventRateFragment : DialogFragment() {

    private lateinit var binding: FragmentEventRateBinding
    private val ticketViewModel: TicketViewModel by viewModels()
    private var ticket = 0
    private var rate = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventRateBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            ticket = requireArguments().getInt("ticketId", 0)

        }

        binding.rating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                rate = rating.toInt()
            }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnAccept.setOnClickListener {
            ticketViewModel.rateTicket(ticket, rate)
            observer()
        }
    }

    private fun observer() {
        ticketViewModel.ticketModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.VISIBLE
                    dialog?.dismiss()
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
            }
        }
    }
}


