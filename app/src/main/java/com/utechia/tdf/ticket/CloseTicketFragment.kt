package com.utechia.tdf.ticket

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.enum.TicketEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCloseTicketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CloseTicketFragment : DialogFragment() {

    private lateinit var binding: FragmentCloseTicketBinding
    private val ticketViewModel:TicketViewModel by viewModels()
    private lateinit var bundle : Bundle
    private var ticketId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCloseTicketBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle = bundleOf(TicketEnum.Type.ticket to TicketEnum.Close.ticket)


        if (arguments != null)
            ticketId = requireArguments().getInt(TicketEnum.Id.ticket, 0)

        binding.btnKeep.setOnClickListener {
            ticketViewModel.closeTicket(ticketId)
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()

        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }
        observer()
    }


    private fun observer() {
        ticketViewModel.ticketModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_closeTicketFragment_to_ticketSystemFragment,bundle)


                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_closeTicketFragment_to_ticketSystemFragment,bundle)
                }
            }
        }
    }
}