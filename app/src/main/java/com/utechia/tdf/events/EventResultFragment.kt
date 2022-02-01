package com.utechia.tdf.events

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.utechia.domain.enum.EventsEnum
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentEventResultBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EventResultFragment : DialogFragment() {

    private lateinit var binding: FragmentEventResultBinding
    private lateinit var bundle : Bundle




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventResultBinding.inflate(inflater, container, false)

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


        binding.exit.setOnClickListener {
            findNavController().navigate(R.id.action_eventResultFragment_to_eventSystemFragment,bundle)
            dialog?.dismiss()        }

        binding.btnKeep.setOnClickListener {
            findNavController().navigate(R.id.action_eventResultFragment_to_eventSystemFragment,bundle)
            dialog?.dismiss()

        }

    }
}