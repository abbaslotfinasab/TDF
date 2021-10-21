package com.utechia.tdf.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentTeaBoyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.food.setOnClickListener {

            val bundle = bundleOf("kind" to 1)
            findNavController().navigate(R.id.action_teaBoyFragment_to_createRefreshmentFragment,bundle)

        }

        binding.drink.setOnClickListener {

            val bundle = bundleOf("kind" to 2)
            findNavController().navigate(R.id.action_teaBoyFragment_to_createRefreshmentFragment,bundle)

        }

        binding.favorite.setOnClickListener {

            val bundle = bundleOf("kind" to 3)
            findNavController().navigate(R.id.action_teaBoyFragment_to_createRefreshmentFragment,bundle)

        }

        binding.order.setOnClickListener {

            val bundle = bundleOf("kind" to 4)
            findNavController().navigate(R.id.action_teaBoyFragment_to_createRefreshmentFragment,bundle)

        }

    }

}