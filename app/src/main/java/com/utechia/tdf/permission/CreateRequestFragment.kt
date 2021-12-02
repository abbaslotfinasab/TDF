package com.utechia.tdf.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateRequestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRequestFragment : DialogFragment() {

    private lateinit var binding: FragmentCreateRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnRequest.setOnClickListener {
            findNavController().navigate(R.id.createRequestFragment_to_confirmRequestFragment)
        }


    }

}