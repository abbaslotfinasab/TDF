package com.utechia.tdf.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utechia.tdf.databinding.FragmentCreateTicketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTicketFragment : Fragment() {

    private lateinit var binding: FragmentCreateTicketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}