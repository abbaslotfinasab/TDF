package com.utechia.tdf.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentTicketSystemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketSystemFragment : Fragment() {

    private lateinit var binding: FragmentTicketSystemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.plus.setOnClickListener {

            findNavController().navigate(R.id.action_ticketSystemFragment_to_createTicketFragment)

        }


    }

}