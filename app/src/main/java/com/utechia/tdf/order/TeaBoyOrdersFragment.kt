package com.utechia.tdf.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utechia.tdf.databinding.FragmentTeaBoyOrdersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyOrdersFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }

}