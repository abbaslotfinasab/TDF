package com.utechia.tdf.events

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.enum.EventsEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCancelEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancelEventFragment : DialogFragment() {

    private lateinit var binding: FragmentCancelEventBinding
    private val eventViewModel:EventDetailsViewModel by viewModels()
    private lateinit var bundle : Bundle
    private var eId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCancelEventBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle = bundleOf(EventsEnum.Type.event to EventsEnum.Upcoming.event )


        if (arguments !=null){
            eId = requireArguments().getInt(EventsEnum.ID.event,0)

        }

        binding.btnKeep.setOnClickListener {
            dialog?.dismiss()
        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            eventViewModel.cancelEvent(eId)
        }
        observer()
    }

    private fun observer() {

        eventViewModel.event.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.VISIBLE
                    findNavController().navigate(R.id.action_cancelEventFragment_to_eventSystemFragment,bundle)
                    dialog?.dismiss()
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.btnKeep.isEnabled = false
                    binding.btnCancel.isEnabled = false
                    binding.exit.isEnabled = false

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_cancelEventFragment_to_eventSystemFragment,bundle)
                    dialog?.dismiss()

                }
            }
        }
    }
}